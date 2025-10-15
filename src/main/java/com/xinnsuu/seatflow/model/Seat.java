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
@Table(name = "seats")
@Data
public class Seat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private int rowNumber;
	private int columnNumber;

	@OneToMany(mappedBy = "seat")
	private Set<SeatAssignment> seatAssignments;

	@ManyToOne
	@JoinColumn(name = "layout_id")
	private ClassroomLayout classroomLayout;
}