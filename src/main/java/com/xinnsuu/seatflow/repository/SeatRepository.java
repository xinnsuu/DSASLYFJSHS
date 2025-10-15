package com.xinnsuu.seatflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xinnsuu.seatflow.model.Seat;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    // Spring Data JPA automatically provides basic CRUD methods
}