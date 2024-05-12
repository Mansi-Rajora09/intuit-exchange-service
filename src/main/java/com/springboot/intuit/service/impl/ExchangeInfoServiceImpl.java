package com.springboot.intuit.service.impl;

import com.springboot.intuit.entity.ExchangeInfo;
import com.springboot.intuit.entity.ExchangeInfoHistory;
import com.springboot.intuit.exception.ResourceNotFoundException;
import com.springboot.intuit.payload.BookingRequest;

import com.springboot.intuit.payload.BookingStatusChangeRequest;
import com.springboot.intuit.payload.ExchangeInfoDto;
import com.springboot.intuit.payload.ExchangeInfoDtoResponse;
import com.springboot.intuit.payload.InstrumentDto;
import com.springboot.intuit.repository.ExchangeInfoHistoryRepository;
import com.springboot.intuit.repository.ExchangeInfoRepository;
import com.springboot.intuit.service.ExchangeInfoService;
import com.springboot.intuit.service.InstrumentService;
import com.springboot.intuit.utils.BookingState;
import com.springboot.intuit.utils.Utility;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExchangeInfoServiceImpl implements ExchangeInfoService {

    private ExchangeInfoRepository exchangeInfoRepository;
    private ExchangeInfoHistoryRepository exchangeInfoHistoryRepository;
    private InstrumentService instrumentService;
    private ModelMapper modelMapper;
    private Utility utility;

    public ExchangeInfoServiceImpl(ExchangeInfoRepository exchangeInfoRepository, InstrumentService instrumentService,
            ModelMapper modelMapper, ExchangeInfoHistoryRepository exchangeInfoHistoryRepository,
            Utility utility) {
        this.exchangeInfoRepository = exchangeInfoRepository;
        this.instrumentService = instrumentService;
        this.modelMapper = modelMapper;
        this.exchangeInfoHistoryRepository = exchangeInfoHistoryRepository;
        this.utility = utility;
    }

    @Override
    public ExchangeInfoDto requestBooking(BookingRequest request) {
        // Perform validation and business logic
        InstrumentDto instrument = instrumentService.getInstrument(request.getInstrumentId());

        // Create a new booking entity
        ExchangeInfo booking = new ExchangeInfo();
        booking.setInstrumentId(instrument.getId());
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

    public ExchangeInfoDtoResponse getUpcomingBookingsByDateRange(Long userId, int pageNo, int pageSize, String sortBy,
            String sortDir,
            String status, Date fromDate, Date toDate) {

        BookingState bookingState = BookingState.valueOf(status);

        // Retrieve upcoming bookings for the given user ID
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        // create Pageable instance
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<ExchangeInfo> posts = exchangeInfoRepository.findUpcomingBookingsByDateRange(pageable, userId, fromDate,
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
    public ExchangeInfoDto changeBookingStatus(BookingStatusChangeRequest bookingStatusChangeRequest, Long bookingId) {
        ExchangeInfo booking = exchangeInfoRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", "id", bookingId));

        if (booking.getState().equals(BookingState.RETURN_REQUESTED)
                && bookingStatusChangeRequest.getState().equals(BookingState.RETURN_ACCEPTED)) {
            exchangeInfoRepository.deleteById(bookingId);
            booking.setState(BookingState.RETURN_ACCEPTED);
            ExchangeInfoHistory history = modelMapper.map(booking, ExchangeInfoHistory.class);
            exchangeInfoHistoryRepository.save(history);
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

}
// ACCEPT AND DENY