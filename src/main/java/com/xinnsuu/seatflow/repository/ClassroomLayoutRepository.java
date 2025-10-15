package com.xinnsuu.seatflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xinnsuu.seatflow.model.ClassroomLayout;

@Repository
public interface ClassroomLayoutRepository extends JpaRepository<ClassroomLayout, Long> {
    // Spring Data JPA automatically provides basic CRUD methods
}