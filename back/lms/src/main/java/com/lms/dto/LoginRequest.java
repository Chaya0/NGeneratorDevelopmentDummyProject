package com.lms.dto;

import jakarta.validation.constraints.NotBlank;

import lombok.Data;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class LoginRequest {
	@NotBlank
    private String username;
	@NotBlank
    private String password;
}
