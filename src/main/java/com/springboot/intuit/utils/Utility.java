package com.springboot.intuit.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.springboot.intuit.entity.ExchangeInfo;
import com.springboot.intuit.payload.BookingRequest;


@Service
public class Utility {


     public  Date stringToDate(String dateString) throws ParseException {
        String format = "yyyy-MM-dd";

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(dateString);
    }


    public void updateBookingInfo(BookingRequest bookingRequest, ExchangeInfo booking) {
        if (bookingRequest.getInstrumentId() != null) {
            booking.setInstrumentId(bookingRequest.getInstrumentId());
        }
        if (bookingRequest.getBorrowUserId() != null) {
            booking.setBorrowUserId(bookingRequest.getBorrowUserId());
        }
        if (bookingRequest.getFromDate() != null) {
            booking.setFromDate(bookingRequest.getFromDate());
        }
        if (bookingRequest.getReturnDate() != null) {
            booking.setReturnDate(bookingRequest.getReturnDate());
        }
        if (bookingRequest.getComment() != null) {
            booking.setComment(bookingRequest.getComment());
        }
        if (bookingRequest.getRenewals() != null) {
            booking.setRenewals(bookingRequest.getRenewals());
        }
        if (bookingRequest.getExchangeType() != null) {
            booking.setExchangeType(bookingRequest.getExchangeType());
        }
    }

    public Long convertToLong(String str) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            // Handle the case where the string cannot be parsed to a long
            System.err.println("Error converting string to long: " + e.getMessage());
            return null; // Or you can throw an exception or return a default value
        }
    }
}
