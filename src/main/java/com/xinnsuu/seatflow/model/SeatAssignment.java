package com.xinnsuu.seatflow.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "seat_assignments", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"layout_id", "rowNumber", "columnNumber"})
})
@Data
public class SeatAssignment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "student_id")
	@NotNull(message = "Assignment must be linked to a student")
	private Student student;

	@ManyToOne
	@JoinColumn(name = "section_id")
	@NotNull(message = "Assignment must be linked to an academic section")
	private AcademicStructure academicStructure;

	@ManyToOne
	@JoinColumn(name = "layout_id")
	@NotNull(message = "Assignment must be linked to a classroom layout")
	private ClassroomLayout classroomLayout;

	// Fields referring to the position coordinates

	@NotNull(message = "Row number is required")
	@Min(value = 1, message = "Row number must be 1 or greater")
	private int rowNumber;

	@NotNull(message = "Column number is required")
	@Min(value = 1, message = "Column number must be 1 or greater")
	private int columnNumber;
}