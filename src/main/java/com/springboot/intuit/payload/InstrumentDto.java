package com.springboot.intuit.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InstrumentDto {
    private Long id;
    private String name;
    private String description;
    private String categoryId;
    private String content;
    private String title;
    private String brand;
    private String userId;
    private Boolean isAvailable;
    private Double ratings = 0.0;
    private String condition;
    private String tags;
    private int limitValue;
    private String instrumentCondition;
    private String availableFromDate;
    private String availableToDate;

}
