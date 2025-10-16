package br.com.jrsr.authenticationapi.dto.auth;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class AuthenticateUserResponseDTO {

    private UUID id;
    private String name;
    private String email;
    private String token;
}
