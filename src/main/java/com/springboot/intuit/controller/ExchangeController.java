package com.springboot.intuit.controller;

import com.springboot.intuit.payload.BookingRequest;
import com.springboot.intuit.payload.BookingStatusChangeRequest;
import com.springboot.intuit.payload.ExchangeInfoDto;
import com.springboot.intuit.payload.ExchangeInfoDtoResponse;
import com.springboot.intuit.service.ExchangeInfoService;
import com.springboot.intuit.utils.AppConstants;
import com.springboot.intuit.utils.Utility;
import com.springboot.intuit.utils.Validation;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;

@RestController
@RequestMapping("/intuit/api/v1/bookings")
public class ExchangeController {

    private ExchangeInfoService bookingService;
    private Validation val;
    private Utility utility;

    public ExchangeController(ExchangeInfoService bookingService, Validation val,Utility utility) {
        this.bookingService = bookingService;
        this.val = val;
        this.utility=utility;
    }

    // Endpoint to initiate a booking request
    @PostMapping("/request")
    public ResponseEntity<ExchangeInfoDto> requestBooking(@RequestBody BookingRequest request) {
        val.validateRequestBooking(request);
        ExchangeInfoDto booking = bookingService.requestBooking(request);
        return new ResponseEntity<>(booking, HttpStatus.CREATED);
    }

    // Endpoint to return a borrowed instrument
    @PostMapping("/{bookingId}/return")
    public ResponseEntity<String> returnInstrument(@PathVariable Long bookingId) {
        String message = bookingService.returnInstrument(bookingId);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/{bookingId}")
    public ResponseEntity<?> updateBookingRequest(@RequestBody BookingRequest bookingRequest,
            @PathVariable Long bookingId) {
        ExchangeInfoDto updatedBooking = bookingService.updateBooking(bookingRequest, bookingId);
        return ResponseEntity.ok(updatedBooking);
    }

    @PutMapping("/change/status/{bookingId}")
    public ResponseEntity<?> changeBookingStatus(@RequestBody BookingStatusChangeRequest bookingStatusChangeRequest,
            @PathVariable Long bookingId) {
        ExchangeInfoDto updatedBooking = bookingService.changeBookingStatus(bookingStatusChangeRequest, bookingId);
        return ResponseEntity.ok(updatedBooking);
    }

    // Endpoint to view booking history for a user
    @GetMapping("/history/{userId}")
    public ResponseEntity<ExchangeInfoDtoResponse> getBookingHistory(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
            @PathVariable Long userId) {
        ExchangeInfoDtoResponse bookingHistory = bookingService.getBookingHistory(userId, pageNo, pageSize, sortBy,
                sortDir);
                //instrument -userid
        return ResponseEntity.ok(bookingHistory);
    }

    // Endpoint to view upcoming booking requests for a user
    @GetMapping("/upcoming/{userId}")
    public ResponseEntity<ExchangeInfoDtoResponse> getUpcomingBookings(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir,
            @RequestParam(value = "state", defaultValue = AppConstants.STATE, required = false) String state,
            @RequestParam(value = "fromDate", required = false) String fromDate,
            @RequestParam(value = "toDate", required = false) String toDate,
            @PathVariable Long userId) throws ParseException {

        ExchangeInfoDtoResponse upcomingBookings;
        if (fromDate != null && toDate != null) {
            Date fromDateFormat = utility.stringToDate(fromDate);
            Date toDateFormat = utility.stringToDate(toDate);

            upcomingBookings = bookingService.getUpcomingBookingsByDateRange(userId, pageNo, pageSize, sortBy, sortDir,
                    state, fromDateFormat, toDateFormat);
        } else {
            upcomingBookings = bookingService.getUpcomingBookings(userId, pageNo, pageSize, sortBy, sortDir, state);
        }
        return ResponseEntity.ok(upcomingBookings);
    }

   


}
