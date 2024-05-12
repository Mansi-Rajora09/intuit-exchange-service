package com.springboot.intuit.utils;

import static org.junit.jupiter.api.Assertions.assertThrows;
import java.util.Calendar;
import java.util.Date;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import jakarta.validation.ValidationException;

public class ValidationTest {
    private final Validation utility = new Validation();

    @ParameterizedTest
    @CsvSource({
            "Test, false", // Valid name
            "12345, true", // Name contains only integers
            "'', true" // Empty name
    })
    public void testValidateName(String name, boolean expectException) {
        if (expectException) {
            assertThrows(ValidationException.class, () -> utility.validateName(name));
        } else {
            // No exception expected
            utility.validateName(name);
        }
    }

    @Test
    public void testValidateCategoryId_Valid() {
        // Call validateCategoryId with a valid categoryId (e.g., containing only
        // digits)
        String validCategoryId = "123";
        utility.validateCategoryId(validCategoryId);

        // No exception should be thrown
    }

    @Test
    public void testValidateCategoryId_Invalid() {
        // Call validateCategoryId with an invalid categoryId (e.g., containing
        // characters)
        String invalidCategoryId = "abc";

        // Assert that a ValidationException is thrown
        assertThrows(ValidationException.class, () -> utility.validateCategoryId(invalidCategoryId));
    }

    @Test
    public void testValidateInstrumentId_ValidInstrumentId() {
        // Call validateInstrumentId with a valid instrument ID
        Long validInstrumentId = 1L;
        // No exception should be thrown for a valid instrument ID
        utility.validateInstrumentId(validInstrumentId);
    }

    @Test
    public void testValidateInstrumentId_NullInstrumentId() {
        // Call validateInstrumentId with a null instrument ID
        Long nullInstrumentId = null;
        // Validate that a ValidationException is thrown for a null instrument ID
        assertThrows(ValidationException.class, () -> utility.validateInstrumentId(nullInstrumentId));
    }

    @Test
    public void testValidateInstrumentId_NegativeInstrumentId() {
        // Call validateInstrumentId with a negative instrument ID
        Long negativeInstrumentId = -1L;
        // Validate that a ValidationException is thrown for a negative instrument ID
        assertThrows(ValidationException.class, () -> utility.validateInstrumentId(negativeInstrumentId));
    }

    @Test
    public void testValidateInstrumentId_ZeroInstrumentId() {
        // Call validateInstrumentId with a zero instrument ID
        Long zeroInstrumentId = 0L;
        // Validate that a ValidationException is thrown for a zero instrument ID
        assertThrows(ValidationException.class, () -> utility.validateInstrumentId(zeroInstrumentId));
    }

    @Test
    public void testValidateUserId_ValidUserId() {
        // Call validateUserId with a valid user ID
        Long validUserId = 1L;
        // No exception should be thrown for a valid user ID
        utility.validateUserId(validUserId);
    }

    @Test
    public void testValidateUserId_NullUserId() {
        // Call validateUserId with a null user ID
        Long nullUserId = null;
        // Validate that a ValidationException is thrown for a null user ID
        assertThrows(ValidationException.class, () -> utility.validateUserId(nullUserId));
    }

    @Test
    public void testValidateUserId_NegativeUserId() {
        // Call validateUserId with a negative user ID
        Long negativeUserId = -1L;
        // Validate that a ValidationException is thrown for a negative user ID
        assertThrows(ValidationException.class, () -> utility.validateUserId(negativeUserId));
    }

    @Test
    public void testValidateUserId_ZeroUserId() {
        // Call validateUserId with a zero user ID
        Long zeroUserId = 0L;
        // Validate that a ValidationException is thrown for a zero user ID
        assertThrows(ValidationException.class, () -> utility.validateUserId(zeroUserId));
    }

    @Test
    public void testValidateExchangeType_ValidExchangeType() {
        // Call validateExchangeType with a valid exchange type
        String validExchangeType = "Sale";
        // No exception should be thrown for a valid exchange type
        utility.validateExchangeType(validExchangeType);
    }

    @Test
    public void testValidateExchangeType_NullExchangeType() {
        // Call validateExchangeType with a null exchange type
        String nullExchangeType = null;
        // Validate that a ValidationException is thrown for a null exchange type
        assertThrows(ValidationException.class, () -> utility.validateExchangeType(nullExchangeType));
    }

    @Test
    public void testValidateExchangeType_EmptyExchangeType() {
        // Call validateExchangeType with an empty exchange type
        String emptyExchangeType = "";
        // Validate that a ValidationException is thrown for an empty exchange type
        assertThrows(ValidationException.class, () -> utility.validateExchangeType(emptyExchangeType));
    }

    @Test
    public void testValidateExchangeType_WhitespaceExchangeType() {
        // Call validateExchangeType with a whitespace-only exchange type
        String whitespaceExchangeType = "   ";
        // Validate that a ValidationException is thrown for a whitespace-only exchange
        // type
        assertThrows(ValidationException.class, () -> utility.validateExchangeType(whitespaceExchangeType));
    }

    @Test
    public void testValidateDates_ValidDates() {
        // Create valid dates: fromDate before returnDate
        Date fromDate = createDate(2024, 5, 1);
        Date returnDate = createDate(2024, 5, 10);

        // No exception should be thrown for valid dates
        utility.validateDates(fromDate, returnDate);
    }

    @Test
    public void testValidateDates_NullFromDate() {
        // Create a null fromDate
        Date fromDate = null;
        // Create a valid returnDate
        Date returnDate = createDate(2024, 5, 10);

        // Validate that a ValidationException is thrown for a null fromDate
        assertThrows(ValidationException.class, () -> utility.validateDates(fromDate, returnDate));
    }

    @Test
    public void testValidateDates_NullReturnDate() {
        // Create a valid fromDate
        Date fromDate = createDate(2024, 5, 1);
        // Create a null returnDate
        Date returnDate = null;

        // Validate that a ValidationException is thrown for a null returnDate
        assertThrows(ValidationException.class, () -> utility.validateDates(fromDate, returnDate));
    }

    @Test
    public void testValidateDates_ReturnDateBeforeFromDate() {
        // Create invalid dates: returnDate before fromDate
        Date fromDate = createDate(2024, 5, 10);
        Date returnDate = createDate(2024, 5, 1);

        // Validate that a ValidationException is thrown for invalid dates
        assertThrows(ValidationException.class, () -> utility.validateDates(fromDate, returnDate));
    }

    // Helper method to create Date objects
    private Date createDate(int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month - 1, day); // Month in Calendar is 0-based
        return calendar.getTime();
    }
}
