package com.xinnsuu.seatflow.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xinnsuu.seatflow.model.SeatAssignment;

import com.xinnsuu.seatflow.model.ClassroomLayout;

@Repository
public interface SeatAssignmentRepository extends JpaRepository<SeatAssignment, Long> {
    boolean existsByClassroomLayoutAndRowNumberAndColumnNumber(ClassroomLayout classroomLayout, int rowNumber, int columnNumber);
    boolean existsByClassroomLayoutAndRowNumberAndColumnNumberAndIdNot(ClassroomLayout classroomLayout, int rowNumber, int columnNumber, Long id);
}