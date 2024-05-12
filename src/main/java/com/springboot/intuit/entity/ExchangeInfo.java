package com.springboot.intuit.entity;

import lombok.*;

import java.util.Date;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.springboot.intuit.utils.BookingState;

import jakarta.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "exchange_info", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class ExchangeInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "instrument_id")
    private Long instrumentId;

    @Column(name = "borrow_user_id")
    private Long borrowUserId;

    @Column(name = "from_date")
    private Date fromDate;

    @Column(name = "return_date")
    private Date returnDate;

    @Column(name = "state")
    private BookingState state; // Should be an enum in a real-world scenario

    @Column(name = "comment")
    private String comment;

    @Column(name = "renewals")
    private Integer renewals = 0;

    @Column(name = "exchange_type")
    private String exchangeType;

    @Column(name = "created_at", nullable = true, updatable = false)
    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = true)
    private Date updatedAt;

}
