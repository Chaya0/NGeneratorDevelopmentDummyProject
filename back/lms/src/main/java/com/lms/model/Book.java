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
@Table(name = "book")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	@Column(nullable = false)
	private String title;
	@Column
	private String isbn;
	@Column
	private LocalDate publishedDate;
	@Column
	private String language;
	@NotNull
	@Column(nullable = false)
	private Integer availableCopies;
	@NotNull
	@Column(nullable = false)
	private Integer totalCopies;
	@ManyToOne
	@JoinColumn(name = "author", referencedColumnName = "id")
	private Author author;

	@ManyToOne
	@JoinColumn(name = "publisher", referencedColumnName = "id")
	private Publisher publisher;

	@ManyToOne
	@JoinColumn(name = "category", referencedColumnName = "id")
	private Category category;

	@ManyToOne
	@JoinColumn(name = "library", referencedColumnName = "id")
	private Library library;


}
