package com.memora.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.memora.api.data.dto.SignUpUserDto;
import com.memora.api.service.AuthService;
import com.memora.api.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class AuthControllerTest {

    @Autowired
    private ObjectMapper objectMapper; // Used to convert objects to JSON

    @MockBean
    private UserService userService;

    @MockBean
    private AuthService authService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testSignUpWithValidUserData() throws Exception {

        // Define your test scenario, mock behaviors, and expectations.
        SignUpUserDto signUpUserDto = new SignUpUserDto();

        signUpUserDto.setFirstName("testuser");
        signUpUserDto.setLastName("testuser");
        signUpUserDto.setUsername("testuser");
        signUpUserDto.setBirthDate(1686146501645L);
        signUpUserDto.setEmail("test@example.com");
        signUpUserDto.setPassword("testpassword");
        signUpUserDto.setConfirmPassword("testpassword");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/signUp")
                        .contentType(MediaType.APPLICATION_JSON) // Set the Content-Type here
                        .content(objectMapper.writeValueAsString(signUpUserDto))) // Send the request body as content
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Username " + signUpUserDto.getUsername() + " successfully registered"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.username").value(signUpUserDto.getUsername()));

    }

//    @Test
//    public void testSignUpWithExistingUserData() throws Exception {
//        // Define your test scenario, mock behaviors, and expectations.
//
//        // Perform an HTTP POST request to the "/api/v1/auth/signUp" endpoint.
//        // You'll need to define the request body as JSON.
//
//        // Assert the expected HTTP status code and response content.
//    }
//
//    @Test
//    public void testSignUpWithInvalidUserPassword() throws Exception {
//        // Define your test scenario, mock behaviors, and expectations.
//
//        // Perform an HTTP POST request to the "/api/v1/auth/signUp" endpoint.
//        // You'll need to define the request body as JSON.
//
//        // Assert the expected HTTP status code and response content.
//    }
//
//    @Test
//    public void testSignInValidCredentials() throws Exception {
//        // Define your test scenario, mock behaviors, and expectations.
//
//        // Perform an HTTP POST request to the "/api/v1/auth/signIn" endpoint.
//        // You'll need to define the request body as JSON.
//
//        // Assert the expected HTTP status code and response content.
//    }
//
//    @Test
//    public void testSignNonExistingUser() throws Exception {
//        // Define your test scenario, mock behaviors, and expectations.
//
//        // Perform an HTTP POST request to the "/api/v1/auth/signIn" endpoint.
//        // You'll need to define the request body as JSON.
//
//        // Assert the expected HTTP status code and response content.
//    }
//
//    @Test
//    public void testSignIncorrectUserPassword() throws Exception {
//        // Define your test scenario, mock behaviors, and expectations.
//
//        // Perform an HTTP POST request to the "/api/v1/auth/signIn" endpoint.
//        // You'll need to define the request body as JSON.
//
//        // Assert the expected HTTP status code and response content.
//    }
}
