package com.springboot.intuit.entity;

import lombok.*;

import java.util.Date;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "instruments")
public class Instrument {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(name = "title", nullable = true)
        private String title;

        @Column(name = "description", nullable = false)
        private String description;

        @Column(name = "content", nullable = true)
        private String content;

        @Column(name = "name", nullable = false)
        private String name;

        @Column(name = "brand", nullable = true)
        private String brand;

        @Column(name = "user_id", nullable = true)
        private String userId;

        @Column(name = "is_available", nullable = false)
        private Boolean isAvailable = true;

        @Column(name = "ratings", nullable = true)
        private Double ratings = 0.0;

        @Column(name = "instrument_condition", nullable = true)
        private String instrumentCondition;

        @Column(name = "tags", nullable = true)
        private String tags;

        @Column(name = "available_from_date", nullable = true)
        private Date availableFromDate;

        @Column(name = "available_to_date", nullable = true)
        private Date availableToDate;

        @Column(name = "limit_value", nullable = true)
        private Integer limitValue = 10;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "category_id")
        private Category category;

        @Column(name = "created_at", nullable = true, updatable = false)
        @CreationTimestamp
        private Date createdAt;

        @UpdateTimestamp
        @Column(name = "updated_at", nullable = true)
        private Date updatedAt;
        // avaiable, waiting for booking, booked

}

// out of scope location of the instruments
// we r not taking care of delibvery and exchsnge
// if there is delay in return therereminder service

/**
 * search on date range
 * advance booking allowed
 * instrument
 * id, name ,description , category_id , brand , owner_id
 * is_available, date_added, ratings, available_from_date, available_to_date ,
 * condition<new,furbished>
 * location,images, tags
 * borrow_history
 * instrument, borrow_user_id, from_date, return_date, state <request,
 * completed>, comment, renewals
 * Renewals: Number of times the borrowing period was renewed.
 * 
 */
// 2 4