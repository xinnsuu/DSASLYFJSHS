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
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "seats")
@Data
public class Seat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull(message = "Row number is required")
	@Min(value = 1, message = "Row number must be 1 or greater")
	private int rowNumber;

	@NotNull(message = "Row number is required")
	@Min(value = 1, message = "Row number must be 1 or greater")
	private int columnNumber;

	@JsonIgnore
	@OneToMany(mappedBy = "seat")
	private Set<SeatAssignment> seatAssignments;

	@ManyToOne
	@JoinColumn(name = "layout_id")
	@NotNull(message = "Seat must be linked to a classroom layout")
	private ClassroomLayout classroomLayout;
}