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
    public List<SeatAssignment> getAllAssignments() {
        return seatAssignmentRepository.findAll();
    }

    @Override
    public Optional<SeatAssignment> getAssignmentById(Long id) {
        return seatAssignmentRepository.findById(id);
    }

    @Override
    public SeatAssignment createAssignment(SeatAssignment assignment) {
        Optional<Student> studentOpt = studentRepository.findById(assignment.getStudent().getStudentId());
        if (studentOpt.isEmpty()) {
            throw new RuntimeException("Student with ID " + assignment.getStudent().getStudentId() + " not found");
        }

        Optional<AcademicStructure> sectionOpt = academicStructureRepository.findById(assignment.getAcademicStructure().getId());
        if (sectionOpt.isEmpty()) {
            throw new RuntimeException("Academic Structure with ID " + assignment.getAcademicStructure().getId() + " not found");
        }

        Long layoutId = assignment.getClassroomLayout().getId();
        Optional<ClassroomLayout> layoutOpt = classroomLayoutRepository.findById(layoutId);
        
        if (layoutOpt.isEmpty()) {
            throw new RuntimeException("Classroom Layout with ID " + layoutId + " not found");
        }

        ClassroomLayout layout = layoutOpt.get();
        Integer row = assignment.getRowNumber();
        Integer col = assignment.getColumnNumber();

        if (row > layout.getRows() || col > layout.getColumns()) {
            throw new RuntimeException("Seat assignment position exceeds classroom layout dimensions");
        }

        assignment.setStudent(studentOpt.get());
        assignment.setAcademicStructure(sectionOpt.get());
        assignment.setClassroomLayout(layout);
        
        return seatAssignmentRepository.save(assignment);
    }

    @Override
    public SeatAssignment updateAssignment(Long id, SeatAssignment updatedAssignment) {
        Optional<SeatAssignment> existingAssignmentOpt = seatAssignmentRepository.findById(id);

        if (existingAssignmentOpt.isPresent()) {
            Optional<Student> studentOpt = studentRepository.findById(updatedAssignment.getStudent().getStudentId());
            if (studentOpt.isEmpty()) {
                throw new RuntimeException("Student with ID " + updatedAssignment.getStudent().getStudentId() + " not found");
            }

            Optional<AcademicStructure> sectionOpt = academicStructureRepository.findById(updatedAssignment.getAcademicStructure().getId());
            if (sectionOpt.isEmpty()) {
                throw new RuntimeException("Academic Structure with ID " + updatedAssignment.getAcademicStructure().getId() + " not found");
            }

            Long newLayoutId = updatedAssignment.getClassroomLayout().getId();
            Optional<ClassroomLayout> layoutOpt = classroomLayoutRepository.findById(newLayoutId);
            
            if (layoutOpt.isEmpty()) {
                throw new RuntimeException("Classroom Layout with ID " + newLayoutId + " not found");
            }

            ClassroomLayout layout = layoutOpt.get();
            Integer row = updatedAssignment.getRowNumber();
            Integer col = updatedAssignment.getColumnNumber();

            if (row > layout.getRows() || col > layout.getColumns()) {
                throw new RuntimeException("Seat assignment position exceeds classroom layout dimensions");
            }
            
            SeatAssignment existingAssignment = existingAssignmentOpt.get();
            
            existingAssignment.setStudent(studentOpt.get());
            existingAssignment.setAcademicStructure(sectionOpt.get());
            existingAssignment.setClassroomLayout(layout);
            existingAssignment.setRowNumber(row);
            existingAssignment.setColumnNumber(col);

            return seatAssignmentRepository.save(existingAssignment);
        } else {
            throw new RuntimeException("Seat Assignment with ID " + id + " not found");
        }
    }

    @Override
    public void deleteAssignment(Long id) {
        if (!seatAssignmentRepository.existsById(id)) {
            throw new RuntimeException("Seat Assignment with ID " + id + " not found");
        }
        seatAssignmentRepository.deleteById(id);
    }
}