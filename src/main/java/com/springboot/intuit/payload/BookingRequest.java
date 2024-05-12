package com.springboot.intuit.payload;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class BookingRequest {

    private Long instrumentId;
    private Long borrowUserId;
    private Date fromDate;
    private Date returnDate;
    private String comment;
    private String exchangeType;
    private Integer renewals;
}
