package com.springboot.intuit.repository;

import com.springboot.intuit.entity.ExchangeInfo;
import com.springboot.intuit.utils.BookingState;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ExchangeInfoRepository extends JpaRepository<ExchangeInfo, Long> {

    List<ExchangeInfo> findByBorrowUserIdOrderByFromDateDesc(Long userId);

    @Query("SELECT b FROM ExchangeInfo b WHERE b.borrowUserId = ?1 AND  b.state =?2")
    Page<ExchangeInfo> findUpcomingBookings(Pageable pageable, Long userId, BookingState state);

    @Query("SELECT b FROM ExchangeInfo b WHERE b.borrowUserId = ?1 AND b.fromDate >= ?2 AND b.returnDate <= ?3 AND b.state =?4")
    Page<ExchangeInfo> findUpcomingBookingsByDateRange(Pageable pageable, Long userId, Date fromDate, Date toDate,
            BookingState state);

}
