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
import com.xinnsuu.seatflow.service.AcademicStructureService;

@Controller
@RequestMapping("/api/sections")
public class AcademicStructureController {

    @Autowired
    private AcademicStructureService academicStructureService;

    @GetMapping
    public ResponseEntity<List<AcademicStructure>> getAllSections() {
        List<AcademicStructure> sections = academicStructureService.getAllSections();
        return new ResponseEntity<>(sections, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AcademicStructure> getSectionById(@PathVariable Long id) {
        Optional<AcademicStructure> structure = academicStructureService.getSectionById(id);

        return structure.map(s -> new ResponseEntity<>(s, HttpStatus.OK))
                        .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<AcademicStructure> createAcademicStructure(
            @Valid @RequestBody AcademicStructure academicStructure) {

        AcademicStructure savedStructure = academicStructureService.createAcademicStructure(academicStructure);
        return new ResponseEntity<>(savedStructure, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AcademicStructure> updateAcademicStructure(
            @PathVariable Long id, 
            @Valid @RequestBody AcademicStructure updatedStructure) {

        try {
            AcademicStructure updated = academicStructureService.updateAcademicStructure(id, updatedStructure);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAcademicStructure(@PathVariable Long id) {
        
        try {
            academicStructureService.deleteAcademicStructure(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}