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
@Table(name = "user")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	@Column(nullable = false, unique = true)
	private String username;
	@NotEmpty
	@Column(nullable = false)
	private String password;
	@NotEmpty
	@Column(nullable = false, unique = true)
	private String email;
	@OneToMany(mappedBy = "user")
	@JsonIgnore
	List<Checkout> checkoutList;

	@OneToMany(mappedBy = "user")
	@JsonIgnore
	List<Fine> fineList;

	@ManyToOne
	@JoinColumn(name = "role", referencedColumnName = "id")
	private Role role;


}
