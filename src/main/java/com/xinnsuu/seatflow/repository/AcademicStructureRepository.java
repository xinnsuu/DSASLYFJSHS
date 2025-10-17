package com.xinnsuu.seatflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xinnsuu.seatflow.model.AcademicStructure;

@Repository
public interface AcademicStructureRepository extends JpaRepository<AcademicStructure, Long> {
    // Spring Data JPA automatically provides basic CRUD methods
}