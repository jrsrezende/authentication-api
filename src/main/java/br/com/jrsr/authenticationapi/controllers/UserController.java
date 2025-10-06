package br.com.jrsr.authenticationapi.controllers;

import br.com.jrsr.authenticationapi.dto.auth.AuthenticateUserRequestDTO;
import br.com.jrsr.authenticationapi.dto.auth.AuthenticateUserResponseDTO;
import br.com.jrsr.authenticationapi.dto.user.CreateUserRequestDTO;
import br.com.jrsr.authenticationapi.dto.user.CreateUserResponseDTO;
import br.com.jrsr.authenticationapi.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "User", description = "User-related operations")
public class UserController {

    @Autowired
    private UserService service;

    @Operation(summary = "Create a new user")
    @ApiResponse(responseCode = "201", description = "User successfully created")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @ApiResponse(responseCode = "409", description = "Email already registered")
    @PostMapping("create")
    public ResponseEntity<CreateUserResponseDTO> createUser(@RequestBody @Valid CreateUserRequestDTO request) {
        CreateUserResponseDTO response = service.createUser(request);
        return ResponseEntity.status(201).body(response);
    }

    @Operation(summary = "Authenticate a user")
    @ApiResponse(responseCode = "200", description = "User successfully authenticated")
    @ApiResponse(responseCode = "400", description = "Invalid request")
    @ApiResponse(responseCode = "401", description = "Authentication failed")
    @PostMapping("authenticate")
    public ResponseEntity<AuthenticateUserResponseDTO> authenticateUser(@RequestBody @Valid AuthenticateUserRequestDTO request) {
        AuthenticateUserResponseDTO response = service.authenticateUser(request);
        return ResponseEntity.status(200).body(response);
    }
}
