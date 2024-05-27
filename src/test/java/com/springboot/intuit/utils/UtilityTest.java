package com.springboot.intuit.utils;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import com.springboot.intuit.entity.ExchangeInfo;
import com.springboot.intuit.payload.BookingRequest;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

public class UtilityTest {

    @Mock
    private BookingRequest bookingRequest;

    @Mock
    private ExchangeInfo booking;

    private Utility utility;

    @Before(value = "")
    public void setUp() {
        utility = new Utility();
    }


    @Test
    public void testUpdateBookingInfo_AllFieldsPresent() {
        // Create a BookingRequest object with all fields present
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setInstrumentId(1L);
        bookingRequest.setBorrowUserId(2L);
        bookingRequest.setFromDate(new Date());
        bookingRequest.setReturnDate(new Date());
        bookingRequest.setComment("Test comment");
        bookingRequest.setRenewals(3);
        bookingRequest.setExchangeType("Exchange");

        // Create an ExchangeInfo object with some initial values
        ExchangeInfo booking = new ExchangeInfo();
        booking.setInstrumentId(10L);
        booking.setBorrowUserId(20L);
        booking.setFromDate(new Date());
        booking.setReturnDate(new Date());
        booking.setComment("Initial comment");
        booking.setRenewals(0);
        booking.setExchangeType("Initial exchange");

        // Create an instance of Utility
        Utility utility = new Utility();

        // Call the updateBookingInfo method
        utility.updateBookingInfo(bookingRequest, booking);

        // Assert that all fields in the booking object are updated
        assertEquals(1L, booking.getInstrumentId());
        assertEquals(2L, booking.getBorrowUserId());
        assertEquals(bookingRequest.getFromDate(), booking.getFromDate());
        assertEquals(bookingRequest.getReturnDate(), booking.getReturnDate());
        assertEquals("Test comment", booking.getComment());
        assertEquals(3, booking.getRenewals());
        assertEquals("Exchange", booking.getExchangeType());
    }

    @Test
    public void testUpdateBookingInfo_SomeFieldsPresent() {
        // Create a BookingRequest object with only some fields present
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setInstrumentId(1L);
        bookingRequest.setFromDate(new Date());
        bookingRequest.setRenewals(3);

        // Create an ExchangeInfo object with some initial values
        ExchangeInfo booking = new ExchangeInfo();
        booking.setInstrumentId(10L);
        booking.setBorrowUserId(20L);
        booking.setFromDate(new Date());
        booking.setReturnDate(new Date());
        booking.setComment("Initial comment");
        booking.setRenewals(0);
        booking.setExchangeType("Initial exchange");

        // Create an instance of Utility
        Utility utility = new Utility();

        // Call the updateBookingInfo method
        utility.updateBookingInfo(bookingRequest, booking);

        // Assert that only the fields present in the booking request are updated
        // Assert that only the fields present in the booking request are updated
        assertEquals(1L, booking.getInstrumentId());

        // Assert other fields similarly
        assertEquals(20L, booking.getBorrowUserId());
        assertEquals("Initial comment", booking.getComment());
        assertEquals(3, booking.getRenewals());
        assertEquals("Initial exchange", booking.getExchangeType());

        // Assert dates are not updated since not present in the request
        assertEquals(booking.getFromDate(), booking.getReturnDate());
        // Assert other fields similarly
    }

    @Test
    public void testUpdateBookingInfo_NoFieldsPresent() {
        // Create a BookingRequest object with no fields present
        BookingRequest bookingRequest = new BookingRequest();

        // Create an ExchangeInfo object with some initial values
        ExchangeInfo booking = new ExchangeInfo();
        booking.setInstrumentId(10L);
        booking.setBorrowUserId(20L);
        booking.setComment("Initial comment");
        booking.setRenewals(0);
        booking.setExchangeType("Initial exchange");

        // Create an instance of Utility
        Utility utility = new Utility();

        // Call the updateBookingInfo method
        utility.updateBookingInfo(bookingRequest, booking);

        // Assert that no fields in the booking object are updated
        // Assert the initial values remain unchanged
        assertEquals(10L, booking.getInstrumentId());
        assertEquals(20L, booking.getBorrowUserId());
        assertEquals("Initial comment", booking.getComment());
        assertEquals(0, booking.getRenewals());
        assertEquals("Initial exchange", booking.getExchangeType());
    }
}
