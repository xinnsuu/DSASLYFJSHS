package com.xinnsuu.seatflow.service;

import java.util.List;
import java.util.Optional;

import com.xinnsuu.seatflow.model.SeatAssignment;

public interface SeatAssignmentService {
    List<SeatAssignment> getAssignmentsBySectionId(Long sectionId);
    Optional<SeatAssignment> getAssignmentByIdAndSectionId(Long id, Long sectionId);
    SeatAssignment createAssignmentForSection(Long sectionId, SeatAssignment assignment);
    SeatAssignment updateAssignmentForSection(Long sectionId, Long id, SeatAssignment updatedAssignment);
    void deleteAssignmentForSection(Long sectionId, Long id);
}