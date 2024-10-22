package com.lms.integration.controller.book;

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

import com.lms.repository.book.BookRepository;
import com.lms.service.book.BookService;
import com.lms.model.Book;
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
class BookControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private BookRepository repository;

	@Autowired
	private ObjectMapper objectMapper;

	private Book entity1;

	@BeforeEach
	void setUp() {
		objectMapper.findAndRegisterModules();
		repository.deleteAll();
		entity1 = new Book();
		entity1.setTitle("title1");
		entity1.setIsbn("isbn1");
		entity1.setPublishedDate(LocalDate.of(2020, 1, 1));
		entity1.setLanguage("language1");
		entity1.setAvailableCopies(1);
		entity1.setTotalCopies(1);
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

		mockMvc.perform(get("/api/book").contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].id").value(entity1.getId()))
			.andExpect(jsonPath("$[0].title").value(entity1.getTitle()))
			.andExpect(jsonPath("$[0].isbn").value(entity1.getIsbn()))
			.andExpect(jsonPath("$[0].language").value(entity1.getLanguage()))
			.andExpect(jsonPath("$[0].availableCopies").value(entity1.getAvailableCopies()))
			.andExpect(jsonPath("$[0].totalCopies").value(entity1.getTotalCopies()))
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

		mockMvc.perform(post("/api/book/search").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(searchDTO)))
			.andExpect(jsonPath("$[0].id").value(entity1.getId()))
			.andExpect(jsonPath("$[0].title").value(entity1.getTitle()))
			.andExpect(jsonPath("$[0].isbn").value(entity1.getIsbn()))
			.andExpect(jsonPath("$[0].language").value(entity1.getLanguage()))
			.andExpect(jsonPath("$[0].availableCopies").value(entity1.getAvailableCopies()))
			.andExpect(jsonPath("$[0].totalCopies").value(entity1.getTotalCopies()))
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

		mockMvc.perform(post("/api/book/searchPageable").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(searchDTO)))
			.andExpect(jsonPath("$.content[0].id").value(entity1.getId()))
			.andExpect(jsonPath("$.content[0].title").value(entity1.getTitle()))
			.andExpect(jsonPath("$.content[0].isbn").value(entity1.getIsbn()))
			.andExpect(jsonPath("$.content[0].language").value(entity1.getLanguage()))
			.andExpect(jsonPath("$.content[0].availableCopies").value(entity1.getAvailableCopies()))
			.andExpect(jsonPath("$.content[0].totalCopies").value(entity1.getTotalCopies()))
			.andExpect(jsonPath("$.size()").value(greaterThan(0)))
			.andExpect(status().isOk());
	}
	@Test
	void testDelete() throws Exception {
		mockMvc.perform(delete("/api/book/" + entity1.getId()))
			.andExpect(status().isNoContent());
	}
	@Test
	void testGetById() throws Exception {
		mockMvc.perform(get("/api/book/" + entity1.getId()).contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id").value(entity1.getId()))
			.andExpect(jsonPath("$.title").value(entity1.getTitle()))
			.andExpect(jsonPath("$.isbn").value(entity1.getIsbn()))
			.andExpect(jsonPath("$.language").value(entity1.getLanguage()))
			.andExpect(jsonPath("$.availableCopies").value(entity1.getAvailableCopies()))
			.andExpect(jsonPath("$.totalCopies").value(entity1.getTotalCopies()))
			.andExpect(status().isOk());
	}
	@Test
	void testInsert() throws Exception {
		Book entity3 = new Book();
		entity3.setTitle("title3");
		entity3.setIsbn("isbn3");
		entity3.setPublishedDate(LocalDate.of(2020, 2, 1));
		entity3.setLanguage("language3");
		entity3.setAvailableCopies(3);
		entity3.setTotalCopies(3);
		mockMvc.perform(post("/api/book").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(entity3)))
			.andExpect(jsonPath("$.title").value(entity3.getTitle()))
			.andExpect(jsonPath("$.isbn").value(entity3.getIsbn()))
			.andExpect(jsonPath("$.language").value(entity3.getLanguage()))
			.andExpect(jsonPath("$.availableCopies").value(entity3.getAvailableCopies()))
			.andExpect(jsonPath("$.totalCopies").value(entity3.getTotalCopies()))
			.andExpect(status().isOk());
	}
	@Test
	void testUpdate() throws Exception {
		entity1.setTitle("title1");
		mockMvc.perform(put("/api/book").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(entity1)))
			.andExpect(jsonPath("$.title").value(entity1.getTitle()))
			.andExpect(status().isOk());
	}
}
