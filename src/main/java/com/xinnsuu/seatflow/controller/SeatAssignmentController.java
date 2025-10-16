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

import com.xinnsuu.seatflow.model.SeatAssignment;
import com.xinnsuu.seatflow.service.SeatAssignmentService;

@Controller
@RequestMapping("/api/assignments")
public class SeatAssignmentController {

	@Autowired
	private SeatAssignmentService seatAssignmentService;

	@GetMapping
    public ResponseEntity<List<SeatAssignment>> getAllAssignments() {
        List<SeatAssignment> assignments = seatAssignmentService.getAllAssignments();
        return new ResponseEntity<>(assignments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SeatAssignment> getAssignmentById(@PathVariable Long id) {
        Optional<SeatAssignment> assignment = seatAssignmentService.getAssignmentById(id);

        return assignment.map(a -> new ResponseEntity<>(a, HttpStatus.OK))
                      .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<SeatAssignment> createAssignment(
            @Valid @RequestBody SeatAssignment assignment) {

        try {
            SeatAssignment savedAssignment = seatAssignmentService.createAssignment(assignment);
            return new ResponseEntity<>(savedAssignment, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SeatAssignment> updateAssignment(
            @PathVariable Long id, 
            @Valid @RequestBody SeatAssignment updatedAssignment) {

        try {
            SeatAssignment updated = seatAssignmentService.updateAssignment(id, updatedAssignment);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("not found")) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        
        try {
            seatAssignmentService.deleteAssignment(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}