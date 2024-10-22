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
import com.lms.model.enums.CheckoutStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "checkout")
public class Checkout {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@Column(nullable = false)
	private LocalDateTime checkoutDate;
	@NotNull
	@Column(nullable = false)
	private LocalDate dueDate;
	@Column
	private LocalDate returnDate;
	@NotNull
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private CheckoutStatus status;
	@ManyToOne
	@JoinColumn(name = "user", referencedColumnName = "id")
	private User user;

	@ManyToOne
	@JoinColumn(name = "book", referencedColumnName = "id")
	private Book book;


}
