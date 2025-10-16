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

import com.xinnsuu.seatflow.model.ClassroomLayout;
import com.xinnsuu.seatflow.service.ClassroomLayoutService;

@Controller
@RequestMapping("/api/layouts")
public class ClassroomLayoutController {
	
	@Autowired
	private ClassroomLayoutService classroomLayoutService;

	@GetMapping
    public ResponseEntity<List<ClassroomLayout>> getAllLayouts() {
        List<ClassroomLayout> layouts = classroomLayoutService.getAllLayouts();
        return new ResponseEntity<>(layouts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassroomLayout> getLayoutById(@PathVariable Long id) {
        Optional<ClassroomLayout> layout = classroomLayoutService.getLayoutById(id);

        return layout.map(l -> new ResponseEntity<>(l, HttpStatus.OK))
                        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ClassroomLayout> createLayout(
            @Valid @RequestBody ClassroomLayout classroomLayout) {

        ClassroomLayout savedLayout = classroomLayoutService.createLayout(classroomLayout);
        return new ResponseEntity<>(savedLayout, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassroomLayout> updateLayout(
            @PathVariable Long id, 
            @Valid @RequestBody ClassroomLayout updatedLayout) {

        try {
            ClassroomLayout updated = classroomLayoutService.updateLayout(id, updatedLayout);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLayout(@PathVariable Long id) {
        
        try {
            classroomLayoutService.deleteLayout(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}