package com.springboot.intuit.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.springboot.intuit.entity.Category;
import com.springboot.intuit.entity.ExchangeInfo;
import com.springboot.intuit.entity.Instrument;
import com.springboot.intuit.payload.BookingRequest;
import com.springboot.intuit.payload.CategoryDto;
import com.springboot.intuit.payload.InstrumentDto;

@Service
public class Utility {

    public void updateInstrumentInfo(InstrumentDto instrumentDto, Long instrumentId, Instrument instrument) {
        if (instrumentDto.getName() != null) {
            instrument.setName(instrumentDto.getName());
        }
        if (instrumentDto.getDescription() != null) {
            instrument.setDescription(instrumentDto.getDescription());
        }
        if (instrumentDto.getId() != null) {
            instrument.setId(instrumentId);
        }
        if (instrumentDto.getIsAvailable() != null) {
            instrument.setIsAvailable(instrumentDto.getIsAvailable());
        }
        if (instrumentDto.getLimitValue() != 0) {
            instrument.setLimitValue(instrumentDto.getLimitValue());
        }
        if (instrumentDto.getInstrumentCondition() != null) {
            instrument.setInstrumentCondition(instrumentDto.getInstrumentCondition());
        }
        if (instrumentDto.getRatings() != null) {
            instrument.setRatings(instrumentDto.getRatings());
        }
        if (instrumentDto.getContent() != null) {
            instrument.setContent(instrumentDto.getContent());
        }
        if (instrumentDto.getBrand() != null) {
            instrument.setBrand(instrumentDto.getBrand());
        }
        if (instrumentDto.getTags() != null) {
            instrument.setTags(instrumentDto.getTags());
        }
    }

     public  Date stringToDate(String dateString) throws ParseException {
        String format = "yyyy-MM-dd";

        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(dateString);
    }

    public void updateCategory(CategoryDto categoryDto, Category category) {
        if (categoryDto.getName() != null) {
            category.setName(categoryDto.getName());
        }
        if (categoryDto.getDescription() != null) {
            category.setDescription(categoryDto.getDescription());
        }
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

}
