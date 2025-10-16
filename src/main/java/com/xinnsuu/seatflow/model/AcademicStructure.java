package com.xinnsuu.seatflow.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import com.xinnsuu.seatflow.model.enums.GradeLevel;
import com.xinnsuu.seatflow.model.enums.Strand;

@Entity
@Table(name = "academic_structures")
@Data
public class AcademicStructure {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Grade Level is required")
	@Enumerated(EnumType.STRING)
	private GradeLevel gradeLevel;

	@NotNull(message = "Grade Level is required")
	@Enumerated(EnumType.STRING)
	private Strand strand;

	@NotBlank(message = "Section Name is required")
	@Size(max = 50)
	private String sectionName;

	@JsonIgnore
	@OneToMany(mappedBy = "academicStructure")
	private Set<Student> students;

	@JsonIgnore
	@OneToMany(mappedBy = "academicStructure")
	private Set<SeatAssignment> seatAssignments;
}