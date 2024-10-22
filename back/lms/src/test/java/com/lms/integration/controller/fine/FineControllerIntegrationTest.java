package com.lms.integration.controller.fine;

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

import com.lms.repository.fine.FineRepository;
import com.lms.service.fine.FineService;
import com.lms.model.Fine;
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
class FineControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private FineRepository repository;

	@Autowired
	private ObjectMapper objectMapper;

	private Fine entity1;

	@BeforeEach
	void setUp() {
		objectMapper.findAndRegisterModules();
		repository.deleteAll();
		entity1 = new Fine();
		entity1.setFineAmount(1.00);
		entity1.setPaid(true);
		entity1.setFineDate(LocalDate.of(2020, 1, 1));
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

		mockMvc.perform(get("/api/fine").contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].id").value(entity1.getId()))
			.andExpect(jsonPath("$[0].fineAmount").value(entity1.getFineAmount()))
			.andExpect(jsonPath("$[0].paid").value(entity1.getPaid()))
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

		mockMvc.perform(post("/api/fine/search").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(searchDTO)))
			.andExpect(jsonPath("$[0].id").value(entity1.getId()))
			.andExpect(jsonPath("$[0].fineAmount").value(entity1.getFineAmount()))
			.andExpect(jsonPath("$[0].paid").value(entity1.getPaid()))
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

		mockMvc.perform(post("/api/fine/searchPageable").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(searchDTO)))
			.andExpect(jsonPath("$.content[0].id").value(entity1.getId()))
			.andExpect(jsonPath("$.content[0].fineAmount").value(entity1.getFineAmount()))
			.andExpect(jsonPath("$.content[0].paid").value(entity1.getPaid()))
			.andExpect(jsonPath("$.size()").value(greaterThan(0)))
			.andExpect(status().isOk());
	}
	@Test
	void testDelete() throws Exception {
		mockMvc.perform(delete("/api/fine/" + entity1.getId()))
			.andExpect(status().isNoContent());
	}
	@Test
	void testGetById() throws Exception {
		mockMvc.perform(get("/api/fine/" + entity1.getId()).contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id").value(entity1.getId()))
			.andExpect(jsonPath("$.fineAmount").value(entity1.getFineAmount()))
			.andExpect(jsonPath("$.paid").value(entity1.getPaid()))
			.andExpect(status().isOk());
	}
	@Test
	void testInsert() throws Exception {
		Fine entity3 = new Fine();
		entity3.setFineAmount(3.00);
		entity3.setPaid(true);
		entity3.setFineDate(LocalDate.of(2020, 2, 1));
		mockMvc.perform(post("/api/fine").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(entity3)))
			.andExpect(jsonPath("$.fineAmount").value(entity3.getFineAmount()))
			.andExpect(jsonPath("$.paid").value(entity3.getPaid()))
			.andExpect(status().isOk());
	}
	@Test
	void testUpdate() throws Exception {
		entity1.setFineAmount(1.00);
		mockMvc.perform(put("/api/fine").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(entity1)))
			.andExpect(jsonPath("$.fineAmount").value(entity1.getFineAmount()))
			.andExpect(status().isOk());
	}
}
