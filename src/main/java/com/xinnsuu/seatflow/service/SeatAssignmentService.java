package com.xinnsuu.seatflow.service;

import java.util.List;
import java.util.Optional;

import com.xinnsuu.seatflow.model.SeatAssignment;

public interface SeatAssignmentService {
    List<SeatAssignment> getAllAssignments();
    Optional<SeatAssignment> getAssignmentById(Long id);
    SeatAssignment createAssignment(SeatAssignment assignment);
    SeatAssignment updateAssignment(Long id, SeatAssignment updatedAssignment);
    void deleteAssignment(Long id);
}