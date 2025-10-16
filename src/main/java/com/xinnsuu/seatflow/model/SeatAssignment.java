package com.xinnsuu.seatflow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "seat_assignments")
@Data
public class SeatAssignment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "seat_id")
	@NotNull(message = "Assignment must be linked to a seat")
	private Seat seat;

	@ManyToOne
	@JoinColumn(name = "student_id")
	@NotNull(message = "Assignment must be linked to a student")
	private Student student;

	@ManyToOne
	@JoinColumn(name = "section_id")
	@NotNull(message = "Assignment must be linked to an academic section")
	private AcademicStructure academicStructure;
}