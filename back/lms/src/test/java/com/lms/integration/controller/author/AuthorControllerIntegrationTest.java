package com.lms.integration.controller.author;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.lms.repository.author.AuthorRepository;
import com.lms.service.author.AuthorService;
import com.lms.model.Author;
import com.lms.specification.*;
import com.lms.model.Permission;

import java.util.*;
import java.time.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.greaterThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@ActiveProfiles("test")
class AuthorControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private AuthorRepository repository;

	@Autowired
	private ObjectMapper objectMapper;

	private Author entity1;

	@BeforeEach
	void setUp() {
		objectMapper.findAndRegisterModules();
		repository.deleteAll();
		entity1 = new Author();
		entity1.setFirstName("firstName1");
		entity1.setLastName("lastName1");
		entity1.setBirthDate(LocalDate.of(2020, 1, 1));
		entity1 = repository.save(entity1);

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
			.andExpect(jsonPath("$.size()").value(greaterThan(0)))
			.andExpect(status().isOk());
	}
	@Test
	void testSearchPost() throws Exception {

		SearchDTO searchDTO = new SearchDTO();
		searchDTO.setPageNumber(0);
		searchDTO.setPageSize(10);
		FilterGroup filterGroup = new FilterGroup();
		filterGroup.setFilters(new ArrayList<SearchFilter>());
		searchDTO.setFilterGroup(filterGroup);

		mockMvc.perform(post("/api/author/search").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(searchDTO)))
			.andExpect(jsonPath("$[0].id").value(entity1.getId()))
			.andExpect(jsonPath("$[0].firstName").value(entity1.getFirstName()))
			.andExpect(jsonPath("$[0].lastName").value(entity1.getLastName()))
			.andExpect(jsonPath("$.size()").value(greaterThan(0)))
			.andExpect(status().isOk());
	}
	@Test
	void testSearchPageablePost() throws Exception {

		SearchDTO searchDTO = new SearchDTO();
		searchDTO.setPageNumber(0);
		searchDTO.setPageSize(10);
		FilterGroup filterGroup = new FilterGroup();
		filterGroup.setFilters(new ArrayList<SearchFilter>());
		searchDTO.setFilterGroup(filterGroup);

		mockMvc.perform(post("/api/author/searchPageable").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(searchDTO)))
			.andExpect(jsonPath("$.content[0].id").value(entity1.getId()))
			.andExpect(jsonPath("$.content[0].firstName").value(entity1.getFirstName()))
			.andExpect(jsonPath("$.content[0].lastName").value(entity1.getLastName()))
			.andExpect(jsonPath("$.size()").value(greaterThan(0)))
			.andExpect(status().isOk());
	}
	@Test
	void testDelete() throws Exception {
		mockMvc.perform(delete("/api/author/" + entity1.getId()))
			.andExpect(status().isNoContent());
	}
	@Test
	void testGetById() throws Exception {
		mockMvc.perform(get("/api/author/" + entity1.getId()).contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id").value(entity1.getId()))
			.andExpect(jsonPath("$.firstName").value(entity1.getFirstName()))
			.andExpect(jsonPath("$.lastName").value(entity1.getLastName()))
			.andExpect(status().isOk());
	}
	@Test
	void testInsert() throws Exception {
		Author entity3 = new Author();
		entity3.setFirstName("firstName3");
		entity3.setLastName("lastName3");
		entity3.setBirthDate(LocalDate.of(2020, 2, 1));
		mockMvc.perform(post("/api/author").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(entity3)))
			.andExpect(jsonPath("$.firstName").value(entity3.getFirstName()))
			.andExpect(jsonPath("$.lastName").value(entity3.getLastName()))
			.andExpect(status().isOk());
	}
	@Test
	void testUpdate() throws Exception {
		entity1.setFirstName("firstName1");
		mockMvc.perform(put("/api/author").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(entity1)))
			.andExpect(jsonPath("$.firstName").value(entity1.getFirstName()))
			.andExpect(status().isOk());
	}
}
