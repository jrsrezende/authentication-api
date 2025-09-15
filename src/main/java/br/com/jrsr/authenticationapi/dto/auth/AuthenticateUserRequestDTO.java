package br.com.jrsr.authenticationapi.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthenticateUserRequestDTO {
    @Email(message = "The access email must be a valid email address.")
    @NotEmpty(message = "The access email is required.")
    private String email;

    @Size(min = 8, message = "The access password must be at least 8 characters long.")
    @NotEmpty(message = "The access password is required.")
    private String password;
}

