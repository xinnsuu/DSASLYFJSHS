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

import com.xinnsuu.seatflow.model.Seat;
import com.xinnsuu.seatflow.model.ClassroomLayout;
import com.xinnsuu.seatflow.repository.SeatRepository;
import com.xinnsuu.seatflow.repository.ClassroomLayoutRepository;

@Controller
@RequestMapping("/api/seats")
public class SeatController {
	
	@Autowired
	private SeatRepository seatRepository;

	@Autowired
	private ClassroomLayoutRepository classroomLayoutRepository;

	@GetMapping
    public ResponseEntity<List<Seat>> getAllSeats() {
        List<Seat> seats = seatRepository.findAll();
        return new ResponseEntity<List<Seat>>(seats, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Seat> getSeatById(@PathVariable Long id) {
        Optional<Seat> seat = seatRepository.findById(id);

        return seat.map(s -> new ResponseEntity<Seat>(s, HttpStatus.OK))
                      .orElse(new ResponseEntity<Seat>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Seat> createSeat(@Valid @RequestBody Seat seat) {

        Long layoutId = seat.getClassroomLayout().getId();
        Optional<ClassroomLayout> layoutOpt = classroomLayoutRepository.findById(layoutId);

        if (layoutOpt.isEmpty()) {
            return new ResponseEntity<Seat>(HttpStatus.BAD_REQUEST);
        }

        seat.setClassroomLayout(layoutOpt.get());
        Seat savedSeat = seatRepository.save(seat);
        return new ResponseEntity<Seat>(savedSeat, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Seat> updateSeat(@PathVariable Long id, @Valid @RequestBody Seat updatedSeat) {

        Optional<Seat> existingSeatOpt = seatRepository.findById(id);

        if (existingSeatOpt.isPresent()) {
            Seat existingSeat = existingSeatOpt.get();

            // 1. Fetch and validate the ClassroomLayout
            Long newLayoutId = updatedSeat.getClassroomLayout().getId();
            Optional<ClassroomLayout> newLayoutOpt = classroomLayoutRepository.findById(newLayoutId);
            
            if (newLayoutOpt.isEmpty()) {
                return new ResponseEntity<Seat>(HttpStatus.BAD_REQUEST);
            }

            // 2. Update properties
            existingSeat.setRowNumber(updatedSeat.getRowNumber());
            existingSeat.setColumnNumber(updatedSeat.getColumnNumber());
            existingSeat.setClassroomLayout(newLayoutOpt.get()); // Update the link

            Seat savedSeat = seatRepository.save(existingSeat);
            return new ResponseEntity<Seat>(savedSeat, HttpStatus.OK);
        } else {
            return new ResponseEntity<Seat>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeat(@PathVariable Long id) {
        
        if (seatRepository.existsById(id)) {
            seatRepository.deleteById(id);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }
}