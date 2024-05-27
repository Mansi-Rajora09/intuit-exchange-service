package com.springboot.intuit.service.impl;

import com.springboot.intuit.entity.ExchangeInfo;
import com.springboot.intuit.entity.ExchangeInfoHistory;
import com.springboot.intuit.exception.ResourceNotFoundException;
import com.springboot.intuit.payload.BookingRequest;

import com.springboot.intuit.payload.BookingStatusChangeRequest;
import com.springboot.intuit.payload.ExchangeInfoDto;
import com.springboot.intuit.payload.ExchangeInfoDtoResponse;
import com.springboot.intuit.repository.ExchangeInfoHistoryRepository;
import com.springboot.intuit.repository.ExchangeInfoRepository;
import com.springboot.intuit.service.ExchangeInfoService;
import com.springboot.intuit.service.UserAPIService;
import com.springboot.intuit.utils.AppConstants;
import com.springboot.intuit.utils.BookingState;
import com.springboot.intuit.utils.Utility;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;
import java.io.IOException;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class ExchangeInfoServiceImpl implements ExchangeInfoService {

        private ExchangeInfoRepository exchangeInfoRepository;
        private ExchangeInfoHistoryRepository exchangeInfoHistoryRepository;
        private ModelMapper modelMapper;
        private Utility utility;
        private UserAPIService userAPIService; // Assuming UserAPIService is your Retrofit service interface

        public ExchangeInfoServiceImpl(ExchangeInfoRepository exchangeInfoRepository,
                        ModelMapper modelMapper, ExchangeInfoHistoryRepository exchangeInfoHistoryRepository,
                        Utility utility, UserAPIService userAPIService) {
                this.exchangeInfoRepository = exchangeInfoRepository;
                this.modelMapper = modelMapper;
                this.exchangeInfoHistoryRepository = exchangeInfoHistoryRepository;
                this.utility = utility;
                this.userAPIService = userAPIService;
        }

        @Override
        public ExchangeInfoDto requestBooking(BookingRequest request) {
                // Perform validation and business logic
                if (request.getExchangeType().equalsIgnoreCase(AppConstants.EXCHANGE_TYPE_BONUS)) {
                        // Call modifyBonusOfUserId for the lender to increase bonus
                        Call<Long> callLender = userAPIService.getBonusOfUserId(request.getBorrowUserId());
                        try {
                                Response<Long> responseLender = callLender.execute();
                                if (responseLender.isSuccessful()) {
                                        Long bonusLender = responseLender.body();
                                        System.out.println("Bonus get for lender (user ID: " + request.getBorrowUserId()
                                                        + "): " + bonusLender);
                                        if (bonusLender <= 0) {
                                                throw new RuntimeException("User dont have bonus available");
                                        }
                                } else {
                                        System.err.println("Failed to get bonus for user ");
                                }
                        } catch (IOException e) {
                                e.printStackTrace();
                                // Handle exception
                        }
                }

                // Create a new booking entity
                ExchangeInfo booking = new ExchangeInfo();
                booking.setInstrumentId(request.getInstrumentId());
                booking.setBorrowUserId(request.getBorrowUserId());
                booking.setFromDate(request.getFromDate());
                booking.setReturnDate(request.getReturnDate());
                booking.setComment(request.getComment());
                booking.setExchangeType(request.getExchangeType());
                booking.setState(BookingState.REQUEST_BOOKING); // Set initial state as REQUESTED

                // Save the booking in the repository
                ExchangeInfo savedInfo = exchangeInfoRepository.save(booking);
                return modelMapper.map(savedInfo, ExchangeInfoDto.class);
        }

        @Override
        public String returnInstrument(Long bookingId) {
                // Fetch the booking from the repository
                ExchangeInfo booking = exchangeInfoRepository.findById(bookingId)
                                .orElseThrow(() -> new RuntimeException("Booking not found"));

                // Check if the booking state is valid for returning
                if (!booking.getState().equals(BookingState.BORROWED)) {
                        throw new RuntimeException("Invalid state for returning the instrument.");
                }

                // Update booking state to COMPLETED
                booking.setState(BookingState.RETURN_REQUESTED);
                exchangeInfoRepository.save(booking);

                return "Instrument returned request raised successfully.";
        }

        @Override
        public ExchangeInfoDtoResponse getBookingHistory(Long userId, int pageNo, int pageSize, String sortBy,
                        String sortDir) {
                Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                                : Sort.by(sortBy).descending();

                // create Pageable instance
                Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

                Page<ExchangeInfoHistory> posts = exchangeInfoHistoryRepository.findAll(pageable);

                // get content for page object
                List<ExchangeInfoHistory> listOfInstruments = posts.getContent();

                List<ExchangeInfoDto> content = listOfInstruments.stream()
                                .map((instrument) -> modelMapper.map(instrument, ExchangeInfoDto.class))
                                .collect(Collectors.toList());

                ExchangeInfoDtoResponse postResponse = new ExchangeInfoDtoResponse();
                postResponse.setContent(content);
                postResponse.setPageNo(posts.getNumber());
                postResponse.setPageSize(posts.getSize());
                postResponse.setTotalElements(posts.getTotalElements());
                postResponse.setTotalPages(posts.getTotalPages());
                postResponse.setLast(posts.isLast());

                return postResponse;
        }

        public ExchangeInfoDtoResponse getUpcomingBookings(Long userId, int pageNo, int pageSize, String sortBy,
                        String sortDir, String status) {
                // Retrieve upcoming bookings for the given user ID

                BookingState bookingState = BookingState.valueOf(status);

                Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                                : Sort.by(sortBy).descending();

                // create Pageable instance
                Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

                Page<ExchangeInfo> posts = exchangeInfoRepository.findUpcomingBookings(pageable, userId, bookingState);

                // get content for page object
                List<ExchangeInfo> listOfInstruments = posts.getContent();

                List<ExchangeInfoDto> content = listOfInstruments.stream()
                                .map((instrument) -> modelMapper.map(instrument, ExchangeInfoDto.class))
                                .collect(Collectors.toList());

                ExchangeInfoDtoResponse postResponse = new ExchangeInfoDtoResponse();
                postResponse.setContent(content);
                postResponse.setPageNo(posts.getNumber());
                postResponse.setPageSize(posts.getSize());
                postResponse.setTotalElements(posts.getTotalElements());
                postResponse.setTotalPages(posts.getTotalPages());
                postResponse.setLast(posts.isLast());

                return postResponse;

        }

        public ExchangeInfoDtoResponse getUpcomingBookingsByDateRange(Long userId, int pageNo, int pageSize,
                        String sortBy,
                        String sortDir,
                        String status, Date fromDate, Date toDate) {

                BookingState bookingState = BookingState.valueOf(status);

                // Retrieve upcoming bookings for the given user ID
                Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                                : Sort.by(sortBy).descending();

                // create Pageable instance
                Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

                Page<ExchangeInfo> posts = exchangeInfoRepository.findUpcomingBookingsByDateRange(pageable, userId,
                                fromDate,
                                toDate, bookingState);

                // get content for page object
                List<ExchangeInfo> listOfInstruments = posts.getContent();

                List<ExchangeInfoDto> content = listOfInstruments.stream()
                                .map((instrument) -> modelMapper.map(instrument, ExchangeInfoDto.class))
                                .collect(Collectors.toList());

                ExchangeInfoDtoResponse postResponse = new ExchangeInfoDtoResponse();
                postResponse.setContent(content);
                postResponse.setPageNo(posts.getNumber());
                postResponse.setPageSize(posts.getSize());
                postResponse.setTotalElements(posts.getTotalElements());
                postResponse.setTotalPages(posts.getTotalPages());
                postResponse.setLast(posts.isLast());

                return postResponse;

        }

        @Override
        public ExchangeInfoDto updateBooking(BookingRequest bookingRequest, Long bookingId) {
                ExchangeInfo booking = exchangeInfoRepository.findById(bookingId)
                                .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", bookingId));

                // Update booking details
                utility.updateBookingInfo(bookingRequest, booking);

                // Save the updated booking
                ExchangeInfo updatedBooking = exchangeInfoRepository.save(booking);

                return modelMapper.map(updatedBooking, ExchangeInfoDto.class);
        }

        @Override
        public ExchangeInfoDto changeBookingStatus(BookingStatusChangeRequest bookingStatusChangeRequest,
                        Long bookingId) {
                ExchangeInfo booking = exchangeInfoRepository.findById(bookingId)
                                .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", bookingId));

                if (booking.getState().equals(BookingState.RETURN_REQUESTED)
                                && bookingStatusChangeRequest.getState().equals(BookingState.RETURN_ACCEPTED)
                                || booking.getState().equals(BookingState.REQUEST_EXCHANGE)
                                                && bookingStatusChangeRequest.getState()
                                                                .equals(BookingState.CANCELLED)) {
                        exchangeInfoRepository.deleteById(bookingId);
                        booking.setState(bookingStatusChangeRequest.getState());
                        ExchangeInfoHistory history = modelMapper.map(booking, ExchangeInfoHistory.class);
                        exchangeInfoHistoryRepository.save(history);
                        // Call the API via Retrofit to fetch additional user information
                        if (booking.getExchangeType().equalsIgnoreCase(AppConstants.EXCHANGE_TYPE_BONUS)) {
                               
                                Long borrowerUserId = booking.getBorrowUserId();

                                // Asynchronously execute modifyBonusOfUserId for the lender and borrower
                                CompletableFuture<Long> lenderFuture = modifyBonusAsync(userAPIService, bookingStatusChangeRequest.getLenderUserId(),
                                                AppConstants.INCREASE_ACTION);
                                CompletableFuture<Long> borrowerFuture = modifyBonusAsync(userAPIService,
                                                borrowerUserId, AppConstants.DECREASE_ACTION);

                                // Wait for both CompletableFuture to complete
                                CompletableFuture.allOf(lenderFuture, borrowerFuture).join();

                              
                                System.out.println("Bonus decreased for borrower (borrowerUserId : " + borrowerUserId
                                                + "): ");

                        }
                        if (booking.getExchangeType().equalsIgnoreCase(AppConstants.EXCHANGE_TYPE_INSTRUMENT)) {
                                
                                Long borrowerUserId = booking.getBorrowUserId();

                                // Asynchronously execute modifyBonusOfUserId for the lender and borrower
                                CompletableFuture<Long> lenderFuture = modifyBonusAsync(userAPIService, bookingStatusChangeRequest.getLenderUserId(),
                                                AppConstants.INCREASE_ACTION);
                                CompletableFuture<Long> borrowerFuture = modifyBonusAsync(userAPIService,
                                                borrowerUserId, AppConstants.INCREASE_ACTION);

                                // Wait for both CompletableFuture to complete
                                CompletableFuture.allOf(lenderFuture, borrowerFuture).join();

                                System.out.println("Bonus decreased for borrower (borrowerUserId : " + borrowerUserId
                                                + "): ");

                        }

                        return modelMapper.map(history, ExchangeInfoDto.class);

                }

                else {
                        // Update booking status
                        booking.setState(bookingStatusChangeRequest.getState());

                        // Save the updated booking
                        ExchangeInfo updatedBooking = exchangeInfoRepository.save(booking);
                        return modelMapper.map(updatedBooking, ExchangeInfoDto.class);

                }

        }

        // Method to asynchronously execute modifyBonusOfUserId
        private CompletableFuture<Long> modifyBonusAsync(UserAPIService userAPIService, Long userId, String action) {
                return CompletableFuture.supplyAsync(() -> {
                        try {
                                Response<Long> response = userAPIService.modifyBonusOfUserId(userId, action).execute();
                                if (response.isSuccessful()) {
                                        return response.body();
                                } else {
                                        System.err.println("Failed to modify bonus for user ID: " + userId);
                                        return null;
                                }
                        } catch (IOException e) {
                                e.printStackTrace();
                                // Handle exception
                                return null;
                        }
                });
        }

        @Override
        public void notifyUsersForReturns() {
                // Get current date
                Date currentDate = new Date();
                System.out.println("Notify user ");

                // Retrieve exchange information where state is borrowed and return date is
                // greater than or equal to today
                List<ExchangeInfo> overdueExchangeInfo = exchangeInfoRepository
                                .findByStateAndReturnDateLessThanEqual(BookingState.BORROWED, currentDate);
                System.out.println("Notify overdueExchangeInfo " + overdueExchangeInfo.size());

                // Iterate through overdue exchange info and notify users
                for (ExchangeInfo exchangeInfo : overdueExchangeInfo) {
                        Long userId = exchangeInfo.getBorrowUserId();
                        // Implement notification logic here (e.g., send an email, push notification,
                        // etc.)
                        System.out.println("Notify user " + userId + " to return instrument with ID "
                                        + exchangeInfo.getInstrumentId());
                }
        }
}
