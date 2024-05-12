package com.springboot.intuit.repository;

import com.springboot.intuit.entity.Category;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByName(String name);

}
