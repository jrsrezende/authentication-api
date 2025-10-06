package br.com.jrsr.authenticationapi;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import br.com.jrsr.authenticationapi.dto.auth.AuthenticateUserRequestDTO;
import br.com.jrsr.authenticationapi.dto.user.CreateUserRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AuthenticateUserTest {

    @Autowired
    private MockMvc mockMvc; // execute REST API tests

    @Autowired
    private ObjectMapper objectMapper; // serialize and deserialize JSON objects

    @Test
    @DisplayName("Authenticate user successfully.")
    public void authenticateSuccessfully() {
        try {
            CreateUserRequestDTO request = new CreateUserRequestDTO();

            Faker faker = new Faker();
            request.setName(faker.name().fullName());
            request.setEmail(faker.internet().emailAddress());
            request.setPassword("StrongP@ssw0rd!");

            AuthenticateUserRequestDTO requestAuthenticate = new AuthenticateUserRequestDTO();
            requestAuthenticate.setEmail(request.getEmail());
            requestAuthenticate.setPassword(request.getPassword());

            MvcResult result = mockMvc.perform(post("/api/v1/users/create")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(request))).andReturn();

            MvcResult resultAuthenticate = mockMvc.perform(post("/api/v1/users/authenticate")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(requestAuthenticate))).andReturn();

            assertEquals(201, result.getResponse().getStatus());
            assertEquals(200, resultAuthenticate.getResponse().getStatus());
        } catch (Exception e) {
            fail("Test Failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Validate all required fields.")
    public void validateRequiredFields() {
        try {
            AuthenticateUserRequestDTO request = new AuthenticateUserRequestDTO();
            request.setEmail(null);
            request.setPassword(null);

            MvcResult result = mockMvc.perform(post("/api/v1/users/authenticate")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(request))).andReturn();

            assertEquals(400, result.getResponse().getStatus());
            assertTrue(result.getResponse().getContentAsString().contains("The access email is required."));
            assertTrue(result.getResponse().getContentAsString().contains("The access password is required."));
        } catch (Exception e) {
            fail("Test Failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Return access denied for invalid user.")
    public void accessDenied() {
        try {
            Faker faker = new Faker();

            AuthenticateUserRequestDTO request = new AuthenticateUserRequestDTO();
            request.setEmail(faker.internet().emailAddress());
            request.setPassword("StrongP@ssw0rd!");

            MvcResult result = mockMvc.perform(post("/api/v1/users/authenticate")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(request))).andReturn();

            assertEquals(401, result.getResponse().getStatus());
            assertTrue(result.getResponse().getContentAsString().contains("Access denied. Invalid user."));
        } catch (Exception e) {
            fail("Test Failed: " + e.getMessage());
        }
    }
}
