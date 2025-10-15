package com.xinnsuu.seatflow.model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "academic_structures")
@Data
public class AcademicStructure {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String gradeLevel;
	private String strand;
	private String sectionName;

	@OneToMany(mappedBy = "academicStructure")
	private Set<Student> students;

	@OneToMany(mappedBy = "academicStructure")
	private Set<SeatAssignment> seatAssignments;
}