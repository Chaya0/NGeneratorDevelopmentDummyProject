package com.lms.integration.controller.reservation;

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

import com.lms.repository.reservation.ReservationRepository;
import com.lms.service.reservation.ReservationService;
import com.lms.model.Reservation;
import com.lms.specification.*;
import com.lms.model.Permission;
import com.lms.model.enums.ReservationStatus;

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
class ReservationControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ReservationRepository repository;

	@Autowired
	private ObjectMapper objectMapper;

	private Reservation entity1;

	@BeforeEach
	void setUp() {
		objectMapper.findAndRegisterModules();
		repository.deleteAll();
		entity1 = new Reservation();
		entity1.setReservationDate(LocalDate.of(2020, 1, 1));
		entity1.setStatus(ReservationStatus.PENDING);
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

		mockMvc.perform(get("/api/reservation").contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].id").value(entity1.getId()))
			.andExpect(jsonPath("$[0].status").value(entity1.getStatus().toString()))
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

		mockMvc.perform(post("/api/reservation/search").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(searchDTO)))
			.andExpect(jsonPath("$[0].id").value(entity1.getId()))
			.andExpect(jsonPath("$[0].status").value(entity1.getStatus().toString()))
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

		mockMvc.perform(post("/api/reservation/searchPageable").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(searchDTO)))
			.andExpect(jsonPath("$.content[0].id").value(entity1.getId()))
			.andExpect(jsonPath("$.content[0].status").value(entity1.getStatus().toString()))
			.andExpect(jsonPath("$.size()").value(greaterThan(0)))
			.andExpect(status().isOk());
	}
	@Test
	void testDelete() throws Exception {
		mockMvc.perform(delete("/api/reservation/" + entity1.getId()))
			.andExpect(status().isNoContent());
	}
	@Test
	void testGetById() throws Exception {
		mockMvc.perform(get("/api/reservation/" + entity1.getId()).contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id").value(entity1.getId()))
			.andExpect(jsonPath("$.status").value(entity1.getStatus().toString()))
			.andExpect(status().isOk());
	}
	@Test
	void testInsert() throws Exception {
		Reservation entity3 = new Reservation();
		entity3.setReservationDate(LocalDate.of(2020, 2, 1));
		entity3.setStatus(ReservationStatus.FULFILLED);
		mockMvc.perform(post("/api/reservation").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(entity3)))
			.andExpect(jsonPath("$.status").value(entity3.getStatus().toString()))
			.andExpect(status().isOk());
	}
	@Test
	void testUpdate() throws Exception {
		entity1.setStatus(ReservationStatus.PENDING);
		mockMvc.perform(put("/api/reservation").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(entity1)))
			.andExpect(jsonPath("$.status").value(entity1.getStatus().toString()))
			.andExpect(status().isOk());
	}
}
