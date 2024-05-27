package com.springboot.intuit.utils;

import java.util.Date;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.springboot.intuit.payload.BookingRequest;

import jakarta.validation.ValidationException;

@Service
public class Validation {
    Pattern pattern = Pattern.compile("\\d+");

    public void validateName(String categoryName) {
        // Define a regex pattern to match only integers
        if (categoryName == null || categoryName.trim().isEmpty()) {
            throw new ValidationException(" name cannot be empty");
        }

        // Check if the category name contains only integers
        if (pattern.matcher(categoryName).matches()) {
            throw new ValidationException(" name cannot contain only integers");
        }
    }

    public void validateUserId(String userId) {
        // Define a regex pattern to match only integers
        if (userId == null || userId.trim().isEmpty()) {
            throw new ValidationException(" userId cannot be empty");
        }
    }

    public void validateDescription(String description) {
        // Add validation rules for the description field if needed
        validateName(description);

    }

    public void validateCategoryId(String categoryId) {
        // Add validation rules for the categoryId field if needed
        if (!pattern.matcher(categoryId).matches()) {
            throw new ValidationException("categoryId cannot contain string");
        }
    }

    public void validateRequestBooking(BookingRequest request) {
        if (request == null) {
            throw new ValidationException("Booking request cannot be null");
        }

        validateInstrumentId(request.getInstrumentId());
        validateUserId(request.getBorrowUserId());
        validateDates(request.getFromDate(), request.getReturnDate());
        validateExchangeType(request.getExchangeType());
    }

    public void validateInstrumentId(Long instrumentId) {
        if (instrumentId == null || instrumentId <= 0) {
            throw new ValidationException("Instrument ID is invalid");
        }
    }

    public void validateUserId(Long userId) {
        if (userId == null || userId <= 0) {
            throw new ValidationException("User ID is invalid");
        }
    }

    public void validateDates(Date fromDate, Date returnDate) {
        if (fromDate == null || returnDate == null || returnDate.before(fromDate)) {
            throw new ValidationException("Invalid booking dates");
        }
    }

    public void validateExchangeType(String exchangeType) {
        if (exchangeType == null || exchangeType.trim().isEmpty()) {
            throw new ValidationException("Exchange type cannot be empty");
        }
    }

}
