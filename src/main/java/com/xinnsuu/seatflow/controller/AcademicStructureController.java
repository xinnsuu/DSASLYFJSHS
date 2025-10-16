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
import com.xinnsuu.seatflow.repository.AcademicStructureRepository;

@Controller
@RequestMapping("/api/sections")
public class AcademicStructureController {

    @Autowired
    private AcademicStructureRepository academicStructureRepository;

    @GetMapping
    public ResponseEntity<List<AcademicStructure>> getAllSections() {
        List<AcademicStructure> sections = academicStructureRepository.findAll();
        return new ResponseEntity<List<AcademicStructure>>(sections, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AcademicStructure> getSectionById(@PathVariable Long id) {
        Optional<AcademicStructure> structure = academicStructureRepository.findById(id);

        return structure.map(s -> new ResponseEntity<AcademicStructure>(s, HttpStatus.OK))
                        .orElse(new ResponseEntity<AcademicStructure>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<AcademicStructure> createAcademicStructure(
            @Valid @RequestBody AcademicStructure academicStructure) {

        AcademicStructure savedStructure = academicStructureRepository.save(academicStructure);
        return new ResponseEntity<AcademicStructure>(savedStructure, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AcademicStructure> updateAcademicStructure(
            @PathVariable Long id, 
            @Valid @RequestBody AcademicStructure updatedStructure) {

        Optional<AcademicStructure> existingStructureOpt = academicStructureRepository.findById(id);

        if (existingStructureOpt.isPresent()) {
            AcademicStructure existingStructure = existingStructureOpt.get();
            
            existingStructure.setGradeLevel(updatedStructure.getGradeLevel());
            existingStructure.setStrand(updatedStructure.getStrand());
            existingStructure.setSectionName(updatedStructure.getSectionName());
            
            AcademicStructure savedStructure = academicStructureRepository.save(existingStructure);
            return new ResponseEntity<AcademicStructure>(savedStructure, HttpStatus.OK);
        } else {
            return new ResponseEntity<AcademicStructure>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAcademicStructure(@PathVariable Long id) {
        
        if (academicStructureRepository.existsById(id)) {
            academicStructureRepository.deleteById(id);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }
}