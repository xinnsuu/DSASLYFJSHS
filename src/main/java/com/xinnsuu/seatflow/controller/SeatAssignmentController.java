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
import com.xinnsuu.seatflow.model.SeatAssignment;
import com.xinnsuu.seatflow.model.Seat;
import com.xinnsuu.seatflow.model.Student;
import com.xinnsuu.seatflow.repository.AcademicStructureRepository;
import com.xinnsuu.seatflow.repository.SeatAssignmentRepository;
import com.xinnsuu.seatflow.repository.SeatRepository;
import com.xinnsuu.seatflow.repository.StudentRepository;

@Controller
@RequestMapping("/api/assignments")
public class SeatAssignmentController {

	@Autowired
	private AcademicStructureRepository academicStructureRepository;
	
	@Autowired
	private SeatAssignmentRepository seatAssignmentRepository;

	@Autowired
	private SeatRepository seatRepository;

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

        // 1. Validate Student
        Optional<Student> studentOpt = studentRepository.findById(assignment.getStudent().getId());
        if (studentOpt.isEmpty()) {
            return new ResponseEntity<SeatAssignment>(HttpStatus.BAD_REQUEST);
        }

        // 2. Validate AcademicStructure
        Optional<AcademicStructure> sectionOpt = academicStructureRepository.findById(assignment.getAcademicStructure().getId());
        if (sectionOpt.isEmpty()) {
            return new ResponseEntity<SeatAssignment>(HttpStatus.BAD_REQUEST);
        }

        // 3. Validate Seat
        Optional<Seat> seatOpt = seatRepository.findById(assignment.getSeat().getId());
        if (seatOpt.isEmpty()) {
            return new ResponseEntity<SeatAssignment>(HttpStatus.BAD_REQUEST);
        }

        // Set the full managed entities before saving
        assignment.setStudent(studentOpt.get());
        assignment.setAcademicStructure(sectionOpt.get());
        assignment.setSeat(seatOpt.get());
        
        SeatAssignment savedAssignment = seatAssignmentRepository.save(assignment);
        return new ResponseEntity<SeatAssignment>(savedAssignment, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeatAssignment> updateAssignment(
            @PathVariable Long id, 
            @Valid @RequestBody SeatAssignment updatedAssignment) {

        Optional<SeatAssignment> existingAssignmentOpt = seatAssignmentRepository.findById(id);

        if (existingAssignmentOpt.isPresent()) {
            SeatAssignment existingAssignment = existingAssignmentOpt.get();

            // 1. Validate Student
            Optional<Student> studentOpt = studentRepository.findById(updatedAssignment.getStudent().getId());
            if (studentOpt.isEmpty()) {
                return new ResponseEntity<SeatAssignment>(HttpStatus.BAD_REQUEST);
            }

            // 2. Validate AcademicStructure
            Optional<AcademicStructure> sectionOpt = academicStructureRepository.findById(updatedAssignment.getAcademicStructure().getId());
            if (sectionOpt.isEmpty()) {
                return new ResponseEntity<SeatAssignment>(HttpStatus.BAD_REQUEST);
            }

            // 3. Validate Seat
            Optional<Seat> seatOpt = seatRepository.findById(updatedAssignment.getSeat().getId());
            if (seatOpt.isEmpty()) {
                return new ResponseEntity<SeatAssignment>(HttpStatus.BAD_REQUEST);
            }
            
            // Update the links
            existingAssignment.setStudent(studentOpt.get());
            existingAssignment.setAcademicStructure(sectionOpt.get());
            existingAssignment.setSeat(seatOpt.get());

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