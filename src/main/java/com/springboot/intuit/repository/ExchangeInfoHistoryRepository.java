package com.springboot.intuit.repository;

import com.springboot.intuit.entity.ExchangeInfoHistory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeInfoHistoryRepository extends JpaRepository<ExchangeInfoHistory, Long> {

        List<ExchangeInfoHistory> findByBorrowUserIdOrderByFromDateDesc(Long userId);

}
