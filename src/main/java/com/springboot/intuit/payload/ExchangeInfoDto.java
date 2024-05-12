package com.springboot.intuit.payload;

import java.util.Date;

import com.springboot.intuit.utils.BookingState;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeInfoDto {
    private Long id;
    private Long instrumentId;
    private Long borrowUserId;
    private Date fromDate;
    private Date returnDate;
    private BookingState state; // Should be an enum in a real-world scenario
    private String comment;
    private Integer renewals;
    private String exchangeType;
}
