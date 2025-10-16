package com.xinnsuu.seatflow.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "students")
@Data
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "First name is required")
	@Size(max = 150)
	private String firstName;

	@Size(max = 150)
	private String middleName;

	@NotBlank(message = "Last name is required")
	@Size(max = 150)
	private String lastName;

	@Size(max = 10)
	private String suffix;

	@NotBlank(message = "Student ID is required")
	@Size(max = 20)
	private String studentId;

	@JsonIgnore
	@OneToMany(mappedBy = "student")
	private Set<SeatAssignment> seatAssignments;

	@ManyToOne
	@JoinColumn(name = "section_id")
	@NotNull(message = "Student must be assigned to an academic structure")
	private AcademicStructure academicStructure;
}