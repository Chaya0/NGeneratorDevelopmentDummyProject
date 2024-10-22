package com.lms.unit.controller.author;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.lms.service.author.AuthorService;
import com.lms.model.Author;
import com.lms.controller.author.AuthorController;
import com.lms.specification.*;
import com.lms.config.security.*;
import com.lms.model.Permission;
import java.util.*;
import java.time.*;

@WebMvcTest(AuthorController.class)
@AutoConfigureMockMvc
class AuthorControllerUnitTest {

	private AuthorController controller;
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AuthorService service;

	@MockBean
	private JwtFilter jwtFilter;

	@MockBean
	private JwtUtil jwtUtil;

	private Author entity1;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		controller = new AuthorController(service);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

		entity1 = new Author();
		entity1.setId(1L);
		entity1.setFirstName("firstName1");
		entity1.setLastName("lastName1");
		entity1.setBirthDate(LocalDate.of(2020, 1, 1));
		List<Author> entities = List.of(entity1);
		when(service.findAll()).thenReturn(entities);

		Authentication authentication = mock(Authentication.class);

		when(authentication.isAuthenticated()).thenReturn(true);

		SecurityContext securityContext = mock(SecurityContext.class);
		when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);

		Permission permission = new Permission();
		permission.setName("Admin");
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(permission);

		Answer<List<GrantedAuthority>> answer = new Answer<List<GrantedAuthority>>() {
			public List<GrantedAuthority> answer(InvocationOnMock invocation) throws Throwable {
				return authorities;
			}
		};
		

		Mockito.when(authentication.getAuthorities()).thenAnswer(answer);

	}

	@Test
	void testFindAll() throws Exception {

		mockMvc.perform(get("/api/author").contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].id").value(entity1.getId()))
			.andExpect(jsonPath("$[0].firstName").value(entity1.getFirstName()))
			.andExpect(jsonPath("$[0].lastName").value(entity1.getLastName()))
			.andExpect(status().isOk());

		verify(service, times(1)).findAll();
	}
	@Test
	void testGetByIdSuccess() throws Exception {

		when(service.findById(1L)).thenReturn(Optional.of(entity1));

		mockMvc.perform(get("/api/author/1").contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id").value(entity1.getId()))
			.andExpect(jsonPath("$.firstName").value(entity1.getFirstName()))
			.andExpect(jsonPath("$.lastName").value(entity1.getLastName()))
			.andExpect(status().isOk());

		verify(service, times(1)).findById(1L);
	}
	@Test
	void testGetByIdNotFound() throws Exception {

		when(service.findById(1L)).thenReturn(Optional.empty());

		assertThrows(Exception.class, () -> {
			mockMvc.perform(get("/api/author/1"));
		});

		verify(service, times(1)).findById(1L);
	}
	@Test
	void testInsert() throws Exception {

		when(service.insert(entity1)).thenReturn(entity1);

		mockMvc.perform(post("/api/author").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().findAndRegisterModules().writeValueAsString(entity1)))
			.andExpect(jsonPath("$.id").value(entity1.getId()))
			.andExpect(jsonPath("$.firstName").value(entity1.getFirstName()))
			.andExpect(jsonPath("$.lastName").value(entity1.getLastName()))
			.andExpect(status().isOk());

		verify(service, times(1)).insert(entity1);
	}
	@Test
	void testUpdateSuccess() throws Exception {

		Author updatedAuthor = new Author();
		updatedAuthor.setId(1L);
		updatedAuthor.setFirstName("firstName1");
		updatedAuthor.setLastName("lastName1");
		updatedAuthor.setBirthDate(LocalDate.of(2020, 1, 1));
		when(service.update(entity1)).thenReturn(updatedAuthor);

		mockMvc.perform(put("/api/author").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().findAndRegisterModules().writeValueAsString(entity1)))
			.andExpect(jsonPath("$.id").value(updatedAuthor.getId()))
			.andExpect(jsonPath("$.firstName").value(updatedAuthor.getFirstName()))
			.andExpect(jsonPath("$.lastName").value(updatedAuthor.getLastName()))
			.andExpect(status().isOk());

		verify(service, times(1)).update(entity1);
	}
	@Test
	void testDeleteSuccess() throws Exception {

		when(service.findById(1L)).thenReturn(Optional.of(entity1));

		mockMvc.perform(delete("/api/author/1")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());

		verify(service, times(1)).deleteById(1L);
	}

	@Test
	void testDeleteNotFound() throws Exception {

		when(service.findById(1L)).thenReturn(Optional.empty());

		assertThrows(Exception.class, () -> {
			mockMvc.perform(delete("/api/author/1"));
		});

		verify(service, times(0)).deleteById(1L);
	}
}
