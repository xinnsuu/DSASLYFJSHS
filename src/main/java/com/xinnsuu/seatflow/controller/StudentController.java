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

import com.xinnsuu.seatflow.model.Student;
import com.xinnsuu.seatflow.model.AcademicStructure;
import com.xinnsuu.seatflow.repository.StudentRepository;
import com.xinnsuu.seatflow.repository.AcademicStructureRepository;

@Controller
@RequestMapping("/api/students")
public class StudentController {
	
	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private AcademicStructureRepository academicStructureRepository;

	@GetMapping
    public ResponseEntity<List<Student>> getAllStudents() {
        List<Student> students = studentRepository.findAll();
        return new ResponseEntity<List<Student>>(students, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Optional<Student> student = studentRepository.findById(id);

        return student.map(s -> new ResponseEntity<Student>(s, HttpStatus.OK))
                      .orElse(new ResponseEntity<Student>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(
            @Valid @RequestBody Student student) {

        // When creating a student, we only receive the AcademicStructure object with its ID set.
        // We must fetch the full, managed AcademicStructure entity before saving the student.
        Long sectionId = student.getAcademicStructure().getId();
        Optional<AcademicStructure> sectionOpt = academicStructureRepository.findById(sectionId);

        if (sectionOpt.isEmpty()) {
            // Cannot create student if the specified section ID does not exist.
            return new ResponseEntity<Student>(HttpStatus.BAD_REQUEST);
        }

        student.setAcademicStructure(sectionOpt.get());
        Student savedStudent = studentRepository.save(student);
        return new ResponseEntity<Student>(savedStudent, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(
            @PathVariable Long id, 
            @Valid @RequestBody Student updatedStudent) {

        Optional<Student> existingStudentOpt = studentRepository.findById(id);

        if (existingStudentOpt.isPresent()) {
            Student existingStudent = existingStudentOpt.get();

            Long newSectionId = updatedStudent.getAcademicStructure().getId();
            Optional<AcademicStructure> newSectionOpt = academicStructureRepository.findById(newSectionId);
            
            if (newSectionOpt.isEmpty()) {
                return new ResponseEntity<Student>(HttpStatus.BAD_REQUEST);
            }

            existingStudent.setFirstName(updatedStudent.getFirstName());
            existingStudent.setLastName(updatedStudent.getLastName());
            existingStudent.setStudentId(updatedStudent.getStudentId());
            existingStudent.setAcademicStructure(newSectionOpt.get()); // Update the link

            Student savedStudent = studentRepository.save(existingStudent);
            return new ResponseEntity<Student>(savedStudent, HttpStatus.OK);
        } else {
            return new ResponseEntity<Student>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        
        if (studentRepository.existsById(id)) {
            studentRepository.deleteById(id);
            return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
    }
}