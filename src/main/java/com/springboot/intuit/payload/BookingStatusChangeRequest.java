package com.springboot.intuit.payload;

import com.springboot.intuit.utils.BookingState;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class BookingStatusChangeRequest {
    private BookingState state;
}
