package com.xinnsuu.seatflow.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.xinnsuu.seatflow.model.ClassroomLayout;
import com.xinnsuu.seatflow.model.SeatAssignment;
import com.xinnsuu.seatflow.model.Student;

@Repository
public interface SeatAssignmentRepository extends JpaRepository<SeatAssignment, Long> {
    boolean existsByClassroomLayoutAndRowNumberAndColumnNumber(ClassroomLayout classroomLayout, int rowNumber, int columnNumber);
    boolean existsByClassroomLayoutAndRowNumberAndColumnNumberAndIdNot(ClassroomLayout classroomLayout, int rowNumber, int columnNumber, Long id);
    boolean existsByStudentAndClassroomLayout(Student student, ClassroomLayout classroomLayout);
    boolean existsByStudentAndClassroomLayoutAndIdNot(Student student, ClassroomLayout classroomLayout, Long id);
    
    List<SeatAssignment> findByAcademicStructureId(Long academicStructureId);
    Optional<SeatAssignment> findByIdAndAcademicStructureId(Long id, Long academicStructureId);
}