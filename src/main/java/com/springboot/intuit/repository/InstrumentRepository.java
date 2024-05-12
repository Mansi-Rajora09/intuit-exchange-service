package com.springboot.intuit.repository;

import com.springboot.intuit.entity.Instrument;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface InstrumentRepository extends JpaRepository<Instrument, Long> {

    Optional<Instrument> findByNameAndUserId(String name, String userId);

}