package com.lms.unit.controller.category;

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

import com.lms.service.category.CategoryService;
import com.lms.model.Category;
import com.lms.controller.category.CategoryController;
import com.lms.specification.*;
import com.lms.config.security.*;
import com.lms.model.Permission;
import java.util.*;
import java.time.*;

@WebMvcTest(CategoryController.class)
@AutoConfigureMockMvc
class CategoryControllerUnitTest {

	private CategoryController controller;
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private CategoryService service;

	@MockBean
	private JwtFilter jwtFilter;

	@MockBean
	private JwtUtil jwtUtil;

	private Category entity1;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		controller = new CategoryController(service);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

		entity1 = new Category();
		entity1.setId(1L);
		entity1.setName("name1");
		List<Category> entities = List.of(entity1);
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

		mockMvc.perform(get("/api/category").contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].id").value(entity1.getId()))
			.andExpect(jsonPath("$[0].name").value(entity1.getName()))
			.andExpect(status().isOk());

		verify(service, times(1)).findAll();
	}
	@Test
	void testGetByIdSuccess() throws Exception {

		when(service.findById(1L)).thenReturn(Optional.of(entity1));

		mockMvc.perform(get("/api/category/1").contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id").value(entity1.getId()))
			.andExpect(jsonPath("$.name").value(entity1.getName()))
			.andExpect(status().isOk());

		verify(service, times(1)).findById(1L);
	}
	@Test
	void testGetByIdNotFound() throws Exception {

		when(service.findById(1L)).thenReturn(Optional.empty());

		assertThrows(Exception.class, () -> {
			mockMvc.perform(get("/api/category/1"));
		});

		verify(service, times(1)).findById(1L);
	}
	@Test
	void testInsert() throws Exception {

		when(service.insert(entity1)).thenReturn(entity1);

		mockMvc.perform(post("/api/category").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().findAndRegisterModules().writeValueAsString(entity1)))
			.andExpect(jsonPath("$.id").value(entity1.getId()))
			.andExpect(jsonPath("$.name").value(entity1.getName()))
			.andExpect(status().isOk());

		verify(service, times(1)).insert(entity1);
	}
	@Test
	void testUpdateSuccess() throws Exception {

		Category updatedCategory = new Category();
		updatedCategory.setId(1L);
		updatedCategory.setName("name1");
		when(service.update(entity1)).thenReturn(updatedCategory);

		mockMvc.perform(put("/api/category").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().findAndRegisterModules().writeValueAsString(entity1)))
			.andExpect(jsonPath("$.id").value(updatedCategory.getId()))
			.andExpect(jsonPath("$.name").value(updatedCategory.getName()))
			.andExpect(status().isOk());

		verify(service, times(1)).update(entity1);
	}
	@Test
	void testDeleteSuccess() throws Exception {

		when(service.findById(1L)).thenReturn(Optional.of(entity1));

		mockMvc.perform(delete("/api/category/1")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());

		verify(service, times(1)).deleteById(1L);
	}

	@Test
	void testDeleteNotFound() throws Exception {

		when(service.findById(1L)).thenReturn(Optional.empty());

		assertThrows(Exception.class, () -> {
			mockMvc.perform(delete("/api/category/1"));
		});

		verify(service, times(0)).deleteById(1L);
	}
}