package com.springboot.intuit.utils;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import com.springboot.intuit.entity.Category;
import com.springboot.intuit.entity.ExchangeInfo;
import com.springboot.intuit.entity.Instrument;
import com.springboot.intuit.payload.BookingRequest;
import com.springboot.intuit.payload.CategoryDto;
import com.springboot.intuit.payload.InstrumentDto;
import com.springboot.intuit.utils.Utility;

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
    public void testUpdateInstrumentInfo() {
        // Mock data
        InstrumentDto instrumentDto = new InstrumentDto();
        instrumentDto.setName("Guitar");
        instrumentDto.setDescription("Acoustic guitar");
        instrumentDto.setId(123L);
        instrumentDto.setIsAvailable(true);
        instrumentDto.setLimitValue(5);
        instrumentDto.setInstrumentCondition("Good");
        instrumentDto.setRatings(4.5);
        instrumentDto.setContent("Guitar content");
        instrumentDto.setBrand("Fender");
        instrumentDto.setTags("Music, Instruments");

        Long instrumentId = 123L;

        Instrument instrument = new Instrument();

        // Mock the Utility class
        Utility utility = new Utility();

        // Call the method to be tested
        utility.updateInstrumentInfo(instrumentDto, instrumentId, instrument);

        // Verify that the fields are updated correctly
        assertEquals("Guitar", instrument.getName());
        assertEquals("Acoustic guitar", instrument.getDescription());
        assertEquals(123L, instrument.getId());
        assertEquals(true, instrument.getIsAvailable());
        assertEquals(5, instrument.getLimitValue());
        assertEquals("Good", instrument.getInstrumentCondition());
        assertEquals(4.5, instrument.getRatings());
        assertEquals("Guitar content", instrument.getContent());
        assertEquals("Fender", instrument.getBrand());
        assertEquals("Music, Instruments", instrument.getTags());
    }

    @Test
    public void testUpdateCategory_NameAndDescriptionProvided() {
        // Mock CategoryDto and Category objects
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("New Name");
        categoryDto.setDescription("New Description");

        Category category = new Category();
        category.setName("Old Name");
        category.setDescription("Old Description");

        // Create an instance of Utility
        Utility utility = new Utility();

        // Call the updateCategory method
        utility.updateCategory(categoryDto, category);

        // Assert that the category's name and description are updated
        assertEquals("New Name", category.getName());
        assertEquals("New Description", category.getDescription());
    }

    @Test
    public void testUpdateCategory_NameProvided() {
        // Mock CategoryDto and Category objects
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setName("New Name");

        Category category = new Category();
        category.setName("Old Name");
        category.setDescription("Old Description");

        // Create an instance of Utility
        Utility utility = new Utility();

        // Call the updateCategory method
        utility.updateCategory(categoryDto, category);

        // Assert that only the category's name is updated
        assertEquals("New Name", category.getName());
        assertEquals("Old Description", category.getDescription()); // Description remains unchanged
    }

    @Test
    public void testUpdateCategory_DescriptionProvided() {
        // Mock CategoryDto and Category objects
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setDescription("New Description");

        Category category = new Category();
        category.setName("Old Name");
        category.setDescription("Old Description");

        // Create an instance of Utility
        Utility utility = new Utility();

        // Call the updateCategory method
        utility.updateCategory(categoryDto, category);

        // Assert that only the category's description is updated
        assertEquals("Old Name", category.getName()); // Name remains unchanged
        assertEquals("New Description", category.getDescription());
    }

    @Test
    public void testUpdateCategory_NoChange() {
        // Mock CategoryDto and Category objects
        CategoryDto categoryDto = new CategoryDto(); // Both name and description are null

        Category category = new Category();
        category.setName("Old Name");
        category.setDescription("Old Description");

        // Create an instance of Utility
        Utility utility = new Utility();

        // Call the updateCategory method
        utility.updateCategory(categoryDto, category);

        // Assert that category remains unchanged
        assertEquals("Old Name", category.getName());
        assertEquals("Old Description", category.getDescription());
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
