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
import com.xinnsuu.seatflow.repository.ClassroomLayoutRepository;

@Controller
@RequestMapping("/api/layouts")
public class ClassroomLayoutController {
	
	@Autowired
	private ClassroomLayoutRepository classroomLayoutRepository;

	@GetMapping
    public ResponseEntity<List<ClassroomLayout>> getAllLayouts() {
        List<ClassroomLayout> layouts = classroomLayoutRepository.findAll();
        return new ResponseEntity<List<ClassroomLayout>>(layouts, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassroomLayout> getLayoutById(@PathVariable Long id) {
        Optional<ClassroomLayout> layout = classroomLayoutRepository.findById(id);

        return layout.map(l -> new ResponseEntity<ClassroomLayout>(l, HttpStatus.OK))
                        .orElse(new ResponseEntity<ClassroomLayout>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<ClassroomLayout> createLayout(
            @Valid @RequestBody ClassroomLayout classroomLayout) {

        ClassroomLayout savedLayout = classroomLayoutRepository.save(classroomLayout);
        return new ResponseEntity<ClassroomLayout>(savedLayout, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassroomLayout> updateLayout(
            @PathVariable Long id, 
            @Valid @RequestBody ClassroomLayout updatedLayout) {

        Optional<ClassroomLayout> existingLayoutOpt = classroomLayoutRepository.findById(id);

        if (existingLayoutOpt.isPresent()) {
            ClassroomLayout existingLayout = existingLayoutOpt.get();
            
            existingLayout.setName(updatedLayout.getName());
            existingLayout.setRows(updatedLayout.getRows());
            existingLayout.setColumns(updatedLayout.getColumns());
            
            ClassroomLayout savedLayout = classroomLayoutRepository.save(existingLayout);
            return new ResponseEntity<ClassroomLayout>(savedLayout, HttpStatus.OK);
        } else {
            return new ResponseEntity<ClassroomLayout>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLayout(@PathVariable Long id) {
        
        if (classroomLayoutRepository.existsById(id)) {
            classroomLayoutRepository.deleteById(id);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }
}