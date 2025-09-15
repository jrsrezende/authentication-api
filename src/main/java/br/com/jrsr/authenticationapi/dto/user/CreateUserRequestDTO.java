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
    @Size(min = 6, max = 100, message = "The username must be between 6 and 100 characters.")
    @Pattern(regexp = "^[A-Za-zÀ-Üà-ü\\s]{6,100}$", message = "The name can only contain letters and spaces and must be between 6 and 100 characters.")
    @NotEmpty(message = "The username is required.")
    private String name;

    @Email(message = "The email must contain a valid address.")
    @NotEmpty(message = "The user's email is required.")
    private String email;

    @Pattern(regexp ="^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$" , message = "The password must contain at least one uppercase letter, one lowercase letter, a number, a special character, and be at least 8 characters long.")
    @NotEmpty(message = "The user's password is required.")
    private String password;
}
