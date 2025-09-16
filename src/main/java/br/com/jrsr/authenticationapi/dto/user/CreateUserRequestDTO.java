package br.com.jrsr.authenticationapi.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateUserRequestDTO {

    @Size(min = 6, max = 100, message = "Username must be between 6 and 100 characters.")
    @Pattern(regexp = "^[A-Za-zÀ-Üà-ü\\s]{6,100}$",
            message = "Name can only contain letters and spaces, and must be 6-100 characters long.")
    @NotEmpty(message = "Username is required.")
    private String name;

    @Email(message = "Please provide a valid email address.")
    @NotEmpty(message = "Email is required.")
    private String email;

    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be at least 8 characters long, with at least one uppercase letter, one lowercase letter, one number, and one special character."
    )
    @NotEmpty(message = "Password is required.")
    private String password;
}

