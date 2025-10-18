package com.xinnsuu.seatflow.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xinnsuu.seatflow.model.AcademicStructure;
import com.xinnsuu.seatflow.model.ClassroomLayout;
import com.xinnsuu.seatflow.model.SeatAssignment;
import com.xinnsuu.seatflow.model.Student;
import com.xinnsuu.seatflow.repository.AcademicStructureRepository;
import com.xinnsuu.seatflow.repository.ClassroomLayoutRepository;
import com.xinnsuu.seatflow.repository.SeatAssignmentRepository;
import com.xinnsuu.seatflow.repository.StudentRepository;

@Service
public class SeatAssignmentServiceImpl implements SeatAssignmentService {

    @Autowired
    private AcademicStructureRepository academicStructureRepository;

    @Autowired
    private ClassroomLayoutRepository classroomLayoutRepository;
	
	@Autowired
	private SeatAssignmentRepository seatAssignmentRepository;

	@Autowired
	private StudentRepository studentRepository;

    @Override
    public List<SeatAssignment> getAssignmentsBySectionId(Long sectionId) {
        Optional<AcademicStructure> sectionOpt = academicStructureRepository.findById(sectionId);
        if (sectionOpt.isEmpty()) {
            throw new RuntimeException("Academic Structure with ID " + sectionId + " not found");
        }
        return seatAssignmentRepository.findByAcademicStructureId(sectionId);
    }

    @Override
    public Optional<SeatAssignment> getAssignmentByIdAndSectionId(Long id, Long sectionId) {
        Optional<AcademicStructure> sectionOpt = academicStructureRepository.findById(sectionId);
        if (sectionOpt.isEmpty()) {
            throw new RuntimeException("Academic Structure with ID " + sectionId + " not found");
        }
        return seatAssignmentRepository.findByIdAndAcademicStructureId(id, sectionId);
    }

    @Override
    public SeatAssignment createAssignmentForSection(Long sectionId, SeatAssignment assignment) {
        Optional<AcademicStructure> sectionOpt = academicStructureRepository.findById(sectionId);
        if (sectionOpt.isEmpty()) {
            throw new RuntimeException("Academic Structure with ID " + sectionId + " not found");
        }

        Optional<Student> studentOpt = studentRepository.findById(assignment.getStudent().getStudentId());
        if (studentOpt.isEmpty()) {
            throw new RuntimeException("Student with ID " + assignment.getStudent().getStudentId() + " not found");
        }

        Long layoutId = assignment.getClassroomLayout().getId();
        Optional<ClassroomLayout> layoutOpt = classroomLayoutRepository.findById(layoutId);
        
        if (layoutOpt.isEmpty()) {
            throw new RuntimeException("Classroom Layout with ID " + layoutId + " not found");
        }

        if (seatAssignmentRepository.existsByStudentAndClassroomLayout(studentOpt.get(), layoutOpt.get())) {
            throw new RuntimeException("Student is already assigned a seat in this classroom layout");
        }

        if (seatAssignmentRepository.existsByClassroomLayoutAndRowNumberAndColumnNumber(layoutOpt.get(), assignment.getRowNumber(), assignment.getColumnNumber())) {
            throw new RuntimeException("Seat is already occupied in this classroom layout");
        }

        ClassroomLayout layout = layoutOpt.get();
        Integer row = assignment.getRowNumber();
        Integer col = assignment.getColumnNumber();

        if (row > layout.getRows() || col > layout.getColumns()) {
            throw new RuntimeException("Seat assignment position exceeds classroom layout dimensions");
        }

        assignment.setAcademicStructure(sectionOpt.get());
        assignment.setStudent(studentOpt.get());
        assignment.setClassroomLayout(layout);
        
        return seatAssignmentRepository.save(assignment);
    }

    @Override
    public SeatAssignment updateAssignmentForSection(Long sectionId, Long id, SeatAssignment updatedAssignment) {
        Optional<AcademicStructure> sectionOpt = academicStructureRepository.findById(sectionId);
        if (sectionOpt.isEmpty()) {
            throw new RuntimeException("Academic Structure with ID " + sectionId + " not found");
        }

        Optional<SeatAssignment> existingAssignmentOpt = seatAssignmentRepository.findByIdAndAcademicStructureId(id, sectionId);
        if (existingAssignmentOpt.isEmpty()) {
            throw new RuntimeException("Seat Assignment with ID " + id + " not found in section " + sectionId);
        }

        Optional<Student> studentOpt = studentRepository.findById(updatedAssignment.getStudent().getStudentId());
        if (studentOpt.isEmpty()) {
            throw new RuntimeException("Student with ID " + updatedAssignment.getStudent().getStudentId() + " not found");
        }

        Long newLayoutId = updatedAssignment.getClassroomLayout().getId();
        Optional<ClassroomLayout> layoutOpt = classroomLayoutRepository.findById(newLayoutId);
        
        if (layoutOpt.isEmpty()) {
            throw new RuntimeException("Classroom Layout with ID " + newLayoutId + " not found");
        }

        if (seatAssignmentRepository.existsByStudentAndClassroomLayoutAndIdNot(studentOpt.get(), layoutOpt.get(), id)) {
            throw new RuntimeException("Student is already assigned a seat in this classroom layout");
        }

        if (seatAssignmentRepository.existsByClassroomLayoutAndRowNumberAndColumnNumberAndIdNot(layoutOpt.get(), updatedAssignment.getRowNumber(), updatedAssignment.getColumnNumber(), id)) {
            throw new RuntimeException("Seat is already occupied in this classroom layout");
        }

        ClassroomLayout layout = layoutOpt.get();
        Integer row = updatedAssignment.getRowNumber();
        Integer col = updatedAssignment.getColumnNumber();

        if (row > layout.getRows() || col > layout.getColumns()) {
            throw new RuntimeException("Seat assignment position exceeds classroom layout dimensions");
        }
        
        SeatAssignment existingAssignment = existingAssignmentOpt.get();
        
        existingAssignment.setAcademicStructure(sectionOpt.get());
        existingAssignment.setStudent(studentOpt.get());
        existingAssignment.setClassroomLayout(layout);
        existingAssignment.setRowNumber(row);
        existingAssignment.setColumnNumber(col);

        return seatAssignmentRepository.save(existingAssignment);
    }

    @Override
    public void deleteAssignmentForSection(Long sectionId, Long id) {
        Optional<SeatAssignment> assignmentOpt = seatAssignmentRepository.findByIdAndAcademicStructureId(id, sectionId);
        if (assignmentOpt.isEmpty()) {
            throw new RuntimeException("Seat Assignment with ID " + id + " not found in section " + sectionId);
        }
        seatAssignmentRepository.deleteById(id);
    }
}