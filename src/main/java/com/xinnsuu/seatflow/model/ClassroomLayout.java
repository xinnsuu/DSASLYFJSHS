package com.xinnsuu.seatflow.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Entity
@Table(name = "classroom_layouts")
@Data
public class ClassroomLayout {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Layout name is required")
	@Size(max = 100)
	private String name;

	@NotNull(message = "Number of rows is required")
	@Min(value = 1, message = "Layout must have at least 1 row")
	private int rows;

	@NotNull(message = "Number of columns is required")
	@Min(value = 1, message = "Layout must have at least 1 column")
	private int columns;

	@JsonIgnore
	@OneToMany(mappedBy = "classroomLayout")
	private Set<Seat> seats;
}