package com.springboot.intuit.service;

import java.util.Date;

import com.springboot.intuit.payload.BookingRequest;
import com.springboot.intuit.payload.BookingStatusChangeRequest;
import com.springboot.intuit.payload.ExchangeInfoDto;
import com.springboot.intuit.payload.ExchangeInfoDtoResponse;

public interface ExchangeInfoService {

    ExchangeInfoDto requestBooking(BookingRequest request);

    ExchangeInfoDtoResponse getBookingHistory(Long userId, int pageNo, int pageSize, String sortBy, String sortDir);

    ExchangeInfoDtoResponse getUpcomingBookings(Long userId, int pageNo, int pageSize, String sortBy, String sortDir,
            String status);

    ExchangeInfoDtoResponse getUpcomingBookingsByDateRange(Long userId, int pageNo, int pageSize, String sortBy,
            String sortDir, String state, Date fromDate, Date toDate);

    String returnInstrument(Long bookingId);

    ExchangeInfoDto updateBooking(BookingRequest bookingRequest, Long bookingId);

    ExchangeInfoDto changeBookingStatus(BookingStatusChangeRequest bookingStatusChangeRequest, Long bookingId);

}