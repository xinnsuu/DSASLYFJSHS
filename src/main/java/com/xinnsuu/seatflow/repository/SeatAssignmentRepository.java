package com.xinnsuu.seatflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xinnsuu.seatflow.model.SeatAssignment;

@Repository
public interface SeatAssignmentRepository extends JpaRepository<SeatAssignment, Long> {
    // Spring Data JPA automatically provides basic CRUD methods
}