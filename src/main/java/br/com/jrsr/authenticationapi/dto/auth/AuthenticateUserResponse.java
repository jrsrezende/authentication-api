package br.com.jrsr.authenticationapi.dto.auth;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class AuthenticateUserResponse {

    private UUID id;
    private String name;
    private String email;
    private String token;
    private LocalDateTime accessedAt;
    private LocalDateTime expiresAt;
}
