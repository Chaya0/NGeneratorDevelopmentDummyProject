package com.lms.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.*;
import java.time.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "author")
public class Author {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	@Column(nullable = false)
	private String firstName;
	@NotEmpty
	@Column(nullable = false)
	private String lastName;
	@Column
	private LocalDate birthDate;
	@OneToMany(mappedBy = "author")
	@JsonIgnore
	List<Book> bookList;


}
