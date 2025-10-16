package com.xinnsuu.seatflow.controller;

import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xinnsuu.seatflow.model.AcademicStructure;
import com.xinnsuu.seatflow.model.ClassroomLayout;
import com.xinnsuu.seatflow.model.SeatAssignment;
import com.xinnsuu.seatflow.model.Student;
import com.xinnsuu.seatflow.repository.AcademicStructureRepository;
import com.xinnsuu.seatflow.repository.ClassroomLayoutRepository;
import com.xinnsuu.seatflow.repository.SeatAssignmentRepository;
import com.xinnsuu.seatflow.repository.StudentRepository;

@Controller
@RequestMapping("/api/assignments")
public class SeatAssignmentController {

	@Autowired
	private AcademicStructureRepository academicStructureRepository;

    @Autowired
    private ClassroomLayoutRepository classroomLayoutRepository;
	
	@Autowired
	private SeatAssignmentRepository seatAssignmentRepository;

	@Autowired
	private StudentRepository studentRepository;

	@GetMapping
    public ResponseEntity<List<SeatAssignment>> getAllAssignments() {
        List<SeatAssignment> assignments = seatAssignmentRepository.findAll();
        return new ResponseEntity<List<SeatAssignment>>(assignments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeatAssignment> getAssignmentById(@PathVariable Long id) {
        Optional<SeatAssignment> assignment = seatAssignmentRepository.findById(id);

        return assignment.map(a -> new ResponseEntity<SeatAssignment>(a, HttpStatus.OK))
                      .orElse(new ResponseEntity<SeatAssignment>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<SeatAssignment> createAssignment(
            @Valid @RequestBody SeatAssignment assignment) {

        Optional<Student> studentOpt = studentRepository.findById(assignment.getStudent().getId());
        if (studentOpt.isEmpty()) {
            return new ResponseEntity<SeatAssignment>(HttpStatus.BAD_REQUEST); // Student not found
        }

        Optional<AcademicStructure> sectionOpt = academicStructureRepository.findById(assignment.getAcademicStructure().getId());
        if (sectionOpt.isEmpty()) {
            return new ResponseEntity<SeatAssignment>(HttpStatus.BAD_REQUEST); // Section not found
        }

        Long layoutId = assignment.getClassroomLayout().getId();
        Optional<ClassroomLayout> layoutOpt = classroomLayoutRepository.findById(layoutId);
        
        if (layoutOpt.isEmpty()) {
            return new ResponseEntity<SeatAssignment>(HttpStatus.BAD_REQUEST); // Layout not found
        }

        ClassroomLayout layout = layoutOpt.get();
        Integer row = assignment.getRowNumber();
        Integer col = assignment.getColumnNumber();

        if (row > layout.getRows() || col > layout.getColumns()) {
            return new ResponseEntity<SeatAssignment>(HttpStatus.BAD_REQUEST); 
        }

        assignment.setStudent(studentOpt.get());
        assignment.setAcademicStructure(sectionOpt.get());
        assignment.setClassroomLayout(layout);
        
        SeatAssignment savedAssignment = seatAssignmentRepository.save(assignment);
        return new ResponseEntity<SeatAssignment>(savedAssignment, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeatAssignment> updateAssignment(
            @PathVariable Long id, 
            @Valid @RequestBody SeatAssignment updatedAssignment) {

        Optional<SeatAssignment> existingAssignmentOpt = seatAssignmentRepository.findById(id);

        if (existingAssignmentOpt.isPresent()) {
            Optional<Student> studentOpt = studentRepository.findById(updatedAssignment.getStudent().getId());
            if (studentOpt.isEmpty()) {
                return new ResponseEntity<SeatAssignment>(HttpStatus.BAD_REQUEST);
            }

            Optional<AcademicStructure> sectionOpt = academicStructureRepository.findById(updatedAssignment.getAcademicStructure().getId());
            if (sectionOpt.isEmpty()) {
                return new ResponseEntity<SeatAssignment>(HttpStatus.BAD_REQUEST);
            }

            Long newLayoutId = updatedAssignment.getClassroomLayout().getId();
            Optional<ClassroomLayout> layoutOpt = classroomLayoutRepository.findById(newLayoutId);
            
            if (layoutOpt.isEmpty()) {
                return new ResponseEntity<SeatAssignment>(HttpStatus.BAD_REQUEST);
            }

            ClassroomLayout layout = layoutOpt.get();
            Integer row = updatedAssignment.getRowNumber();
            Integer col = updatedAssignment.getColumnNumber();

            if (row > layout.getRows() || col > layout.getColumns()) {
                return new ResponseEntity<SeatAssignment>(HttpStatus.BAD_REQUEST);
            }
            
            SeatAssignment existingAssignment = existingAssignmentOpt.get();
            
            existingAssignment.setStudent(studentOpt.get());
            existingAssignment.setAcademicStructure(sectionOpt.get());
            existingAssignment.setClassroomLayout(layout);
            existingAssignment.setRowNumber(row);
            existingAssignment.setColumnNumber(col);

            SeatAssignment savedAssignment = seatAssignmentRepository.save(existingAssignment);
            return new ResponseEntity<SeatAssignment>(savedAssignment, HttpStatus.OK);
        } else {
            return new ResponseEntity<SeatAssignment>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        
        if (seatAssignmentRepository.existsById(id)) {
            seatAssignmentRepository.deleteById(id);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }
}