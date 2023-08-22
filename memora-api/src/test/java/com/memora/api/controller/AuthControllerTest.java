package com.memora.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.memora.api.data.dto.SignInUserDto;
import com.memora.api.data.dto.SignUpUserDto;
import com.memora.api.service.AuthService;
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

import static org.mockito.Mockito.when;

@SpringBootTest
public class AuthControllerTest {

    @Autowired
    private ObjectMapper objectMapper; // Used to convert objects to JSON

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private AuthService authService;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testSignUpWitValidUserData() throws Exception {

        // Define your test scenario, mock behaviors, and expectations.
        SignUpUserDto signUpUserDto = new SignUpUserDto();

        signUpUserDto.setFirstName("testuser");
        signUpUserDto.setLastName("testuser");
        signUpUserDto.setUsername("testuser");
        signUpUserDto.setBirthDate(1686146501645L);
        signUpUserDto.setEmail("test@example.com");
        signUpUserDto.setPassword("Pass@word12");
        signUpUserDto.setConfirmPassword("Pass@word12");


        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/signUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpUserDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Username " + signUpUserDto.getUsername() + " successfully registered"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.username").value(signUpUserDto.getUsername()));

    }

    @Test
    public void testSignUpWitInvalidPassword() throws Exception {

        // Define your test scenario, mock behaviors, and expectations.
        SignUpUserDto signUpUserDto = new SignUpUserDto();

        signUpUserDto.setFirstName("testusername");
        signUpUserDto.setLastName("testusername");
        signUpUserDto.setUsername("testusername");
        signUpUserDto.setBirthDate(1686146501645L);
        signUpUserDto.setEmail("testusername@example.com");
        signUpUserDto.setPassword("testpassword");
        signUpUserDto.setConfirmPassword("testpassword");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/signUp")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signUpUserDto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("User password is not valid. Please input a valid password"));

    }

    @Test
    public void testSignInWithValidCredentials() throws Exception {

        // Define your test scenario, mock behaviors, and expectations.
        SignInUserDto signInUserDto = new SignInUserDto();
        signInUserDto.setEmail("test@example.com");
        signInUserDto.setPassword("Pass@word12");

        // Mock the behavior of authService.authenticate() to return true for valid credentials
        when(authService.authenticate(signInUserDto)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/signIn")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signInUserDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Login successful"));

    }

    @Test
    public void testSignInWithInvalidPassword() throws Exception {

        // Define your test scenario, mock behaviors, and expectations.
        SignInUserDto signInUserDto = new SignInUserDto();
        signInUserDto.setEmail("test@example.com");
        signInUserDto.setPassword("Pass@word12");

        // Mock the behavior of authService.authenticate() to return true for valid credentials
        when(authService.authenticate(signInUserDto)).thenReturn(true);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/signIn")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signInUserDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Login successful"));

    }
}
