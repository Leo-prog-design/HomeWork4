package com.example.HW4;

import com.example.HW4.controller.UserController;
import com.example.HW4.dto.UserDto;
import com.example.HW4.entity.User;
import com.example.HW4.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class Hw4ApplicationTests {

	@Autowired
	private MockMvc mvc;

	@MockitoBean
	private UserService userService;

	@Autowired
	private ObjectMapper objectMapper;

	private User testUser;
	private UserDto testUserDto;

	@BeforeEach
	void setUp() {
		testUser = new User("John Doe", "john@example.com", 30);
		testUserDto = new UserDto(30, "john@example.com", "John Doe");
	}

	@Test
	void testReturnCreateUser() throws Exception {
		when(userService.createUser(any(User.class))).thenReturn(testUserDto);

		mvc.perform(post("/api/createuser")
						.contentType(String.valueOf(MediaType.APPLICATION_JSON))
						.content(objectMapper.writeValueAsString(testUser)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.name").value("John Doe"))
				.andExpect(jsonPath("$.email").value("john@example.com"))
				.andExpect(jsonPath("$.age").value(30));

		verify(userService, times(1)).createUser(any(User.class));
	}

	@Test
	void updateUser_ShouldReturnUpdatedUser() throws Exception {
		Long userId = 1L;
		UserDto updatedUserDto = new UserDto(31, "john.updated@example.com", "John Updated");
		when(userService.updateUserById(eq(userId), any(User.class))).thenReturn(updatedUserDto);

		mvc.perform(put("/api/users/{id}", userId)
						.contentType(String.valueOf(MediaType.APPLICATION_JSON))
						.content(objectMapper.writeValueAsString(testUser)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("John Updated"))
				.andExpect(jsonPath("$.email").value("john.updated@example.com"))
				.andExpect(jsonPath("$.age").value(31));

		verify(userService).updateUserById(eq(userId), any(User.class));
	}

	@Test
	void deleteUser_ShouldReturnNoContent() throws Exception {
		Long userId = 1L;
		doNothing().when(userService).deleteUserById(userId);

		mvc.perform(delete("/api/users/{id}", userId)
						.contentType(String.valueOf(MediaType.APPLICATION_JSON)))
				.andExpect(status().isNoContent());

		verify(userService).deleteUserById(userId);
	}

}
