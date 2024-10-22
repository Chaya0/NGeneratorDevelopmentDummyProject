package com.lms.unit.controller.book;

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

import com.lms.service.book.BookService;
import com.lms.model.Book;
import com.lms.controller.book.BookController;
import com.lms.specification.*;
import com.lms.config.security.*;
import com.lms.model.Permission;
import java.util.*;
import java.time.*;

@WebMvcTest(BookController.class)
@AutoConfigureMockMvc
class BookControllerUnitTest {

	private BookController controller;
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BookService service;

	@MockBean
	private JwtFilter jwtFilter;

	@MockBean
	private JwtUtil jwtUtil;

	private Book entity1;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		controller = new BookController(service);
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

		entity1 = new Book();
		entity1.setId(1L);
		entity1.setTitle("title1");
		entity1.setIsbn("isbn1");
		entity1.setPublishedDate(LocalDate.of(2020, 1, 1));
		entity1.setLanguage("language1");
		entity1.setAvailableCopies(1);
		entity1.setTotalCopies(1);
		List<Book> entities = List.of(entity1);
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

		mockMvc.perform(get("/api/book").contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$[0].id").value(entity1.getId()))
			.andExpect(jsonPath("$[0].title").value(entity1.getTitle()))
			.andExpect(jsonPath("$[0].isbn").value(entity1.getIsbn()))
			.andExpect(jsonPath("$[0].language").value(entity1.getLanguage()))
			.andExpect(jsonPath("$[0].availableCopies").value(entity1.getAvailableCopies()))
			.andExpect(jsonPath("$[0].totalCopies").value(entity1.getTotalCopies()))
			.andExpect(status().isOk());

		verify(service, times(1)).findAll();
	}
	@Test
	void testGetByIdSuccess() throws Exception {

		when(service.findById(1L)).thenReturn(Optional.of(entity1));

		mockMvc.perform(get("/api/book/1").contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id").value(entity1.getId()))
			.andExpect(jsonPath("$.title").value(entity1.getTitle()))
			.andExpect(jsonPath("$.isbn").value(entity1.getIsbn()))
			.andExpect(jsonPath("$.language").value(entity1.getLanguage()))
			.andExpect(jsonPath("$.availableCopies").value(entity1.getAvailableCopies()))
			.andExpect(jsonPath("$.totalCopies").value(entity1.getTotalCopies()))
			.andExpect(status().isOk());

		verify(service, times(1)).findById(1L);
	}
	@Test
	void testGetByIdNotFound() throws Exception {

		when(service.findById(1L)).thenReturn(Optional.empty());

		assertThrows(Exception.class, () -> {
			mockMvc.perform(get("/api/book/1"));
		});

		verify(service, times(1)).findById(1L);
	}
	@Test
	void testInsert() throws Exception {

		when(service.insert(entity1)).thenReturn(entity1);

		mockMvc.perform(post("/api/book").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().findAndRegisterModules().writeValueAsString(entity1)))
			.andExpect(jsonPath("$.id").value(entity1.getId()))
			.andExpect(jsonPath("$.title").value(entity1.getTitle()))
			.andExpect(jsonPath("$.isbn").value(entity1.getIsbn()))
			.andExpect(jsonPath("$.language").value(entity1.getLanguage()))
			.andExpect(jsonPath("$.availableCopies").value(entity1.getAvailableCopies()))
			.andExpect(jsonPath("$.totalCopies").value(entity1.getTotalCopies()))
			.andExpect(status().isOk());

		verify(service, times(1)).insert(entity1);
	}
	@Test
	void testUpdateSuccess() throws Exception {

		Book updatedBook = new Book();
		updatedBook.setId(1L);
		updatedBook.setTitle("title1");
		updatedBook.setIsbn("isbn1");
		updatedBook.setPublishedDate(LocalDate.of(2020, 1, 1));
		updatedBook.setLanguage("language1");
		updatedBook.setAvailableCopies(1);
		updatedBook.setTotalCopies(1);
		when(service.update(entity1)).thenReturn(updatedBook);

		mockMvc.perform(put("/api/book").contentType(MediaType.APPLICATION_JSON).content(new ObjectMapper().findAndRegisterModules().writeValueAsString(entity1)))
			.andExpect(jsonPath("$.id").value(updatedBook.getId()))
			.andExpect(jsonPath("$.title").value(updatedBook.getTitle()))
			.andExpect(jsonPath("$.isbn").value(updatedBook.getIsbn()))
			.andExpect(jsonPath("$.language").value(updatedBook.getLanguage()))
			.andExpect(jsonPath("$.availableCopies").value(updatedBook.getAvailableCopies()))
			.andExpect(jsonPath("$.totalCopies").value(updatedBook.getTotalCopies()))
			.andExpect(status().isOk());

		verify(service, times(1)).update(entity1);
	}
	@Test
	void testDeleteSuccess() throws Exception {

		when(service.findById(1L)).thenReturn(Optional.of(entity1));

		mockMvc.perform(delete("/api/book/1")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isNoContent());

		verify(service, times(1)).deleteById(1L);
	}

	@Test
	void testDeleteNotFound() throws Exception {

		when(service.findById(1L)).thenReturn(Optional.empty());

		assertThrows(Exception.class, () -> {
			mockMvc.perform(delete("/api/book/1"));
		});

		verify(service, times(0)).deleteById(1L);
	}
}
