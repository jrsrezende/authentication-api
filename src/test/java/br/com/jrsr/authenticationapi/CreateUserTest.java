package br.com.jrsr.authenticationapi;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import br.com.jrsr.authenticationapi.dto.user.CreateUserRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CreateUserTest {

    @Autowired
    private MockMvc mockMvc; //executar testes na api rest

    @Autowired
    private ObjectMapper objectMapper;  //serializar e desserializar objetos JSON

    private static String userEmail;

    @Test
    @DisplayName("Should successfully create a user.")
    @Order(1)
    public void createUserSuccessfully() {
        try {
            CreateUserRequestDTO request = new CreateUserRequestDTO();
            Faker faker = new Faker();
            request.setName(faker.name().fullName());
            request.setEmail(faker.internet().emailAddress());
            request.setPassword("StrongP@ssw0rd!");

            var result = mockMvc.perform(post("/api/v1/users/create")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(request))).andReturn();

            assertEquals(201, result.getResponse().getStatus());

            userEmail = request.getEmail();
        } catch (Exception e) {
            fail("Test Failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Should validate all required fields.")
    @Order(2)
    public void validateRequiredFields() {
        try {
            CreateUserRequestDTO request = new CreateUserRequestDTO();
            request.setName(null);
            request.setEmail(null);
            request.setPassword(null);

            MvcResult result = mockMvc.perform(post("/api/v1/users/create")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(request))).andReturn();

            assertEquals(400, result.getResponse().getStatus());

            assertTrue(result.getResponse().getContentAsString().contains("Username is required."));
            assertTrue(result.getResponse().getContentAsString().contains("Email is required."));
            assertTrue(result.getResponse().getContentAsString().contains("Password is required."));
        } catch (Exception e) {
            fail("Test Failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Should validate the strong password.")
    @Order(3)
    public void validateStrongPassword() {
        try {
            CreateUserRequestDTO request = new CreateUserRequestDTO();
            Faker faker = new Faker();
            request.setName(faker.name().fullName());
            request.setEmail(faker.internet().emailAddress());
            request.setPassword("weakpass");

            MvcResult result = mockMvc.perform(post("/api/v1/users/create")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(request))).andReturn();

            assertEquals(400, result.getResponse().getStatus());

            assertTrue(result.getResponse().getContentAsString().contains("Password must be at least 8 characters long, with at least one uppercase letter, one lowercase letter, one number, and one special character."));
        } catch (Exception e) {
            fail("Test Failed: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Should not allow registering duplicate emails for the user.")
    @Order(4)
    public void checkEmailAlreadyRegistered() {
        try {
            CreateUserRequestDTO request = new CreateUserRequestDTO();
            Faker faker = new Faker();
            request.setName(faker.name().fullName());
            request.setEmail(userEmail);
            request.setPassword("StrongP@ssw0rd!");

            MvcResult result = mockMvc.perform(post("/api/v1/users/create")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(request))).andReturn();

            assertEquals(409, result.getResponse().getStatus());

            assertTrue(result.getResponse().getContentAsString().contains("Email already registered."));
        } catch (Exception e) {
            fail("Test Failed: " + e.getMessage());
        }
    }
}
