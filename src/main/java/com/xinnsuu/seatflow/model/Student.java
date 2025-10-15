package com.xinnsuu.seatflow.model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "students")
@Data
public class Student {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String firstName;
	private String middleName;
	private String lastName;
	private String studentId;

	@OneToMany(mappedBy = "student")
	private Set<SeatAssignment> seatAssignments;

	@ManyToOne
	@JoinColumn(name = "section_id")
	private AcademicStructure academicStructure;
}