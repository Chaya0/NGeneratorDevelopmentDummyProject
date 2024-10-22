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
import java.util.stream.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "role")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	@Column(nullable = false, unique = true)
	private String name;
	@Column
	private String description;
	@ManyToMany(fetch =  FetchType.EAGER)
	@JsonIgnore
	@JoinTable(name = "role_permission",
		joinColumns = @JoinColumn(name = "role", referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "permission", referencedColumnName = "id"))
	private List<Permission> permissionList;


	@JsonIgnore
	public List<GrantedAuthority> getAuthorities() {
		return permissionList.stream().map(permission -> new SimpleGrantedAuthority(permission.getName())).collect(Collectors.toList());
	}

}
