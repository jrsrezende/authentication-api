package br.com.jrsr.authenticationapi.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class CreateUserResponseDTO {

    private UUID id;
    private String name;
    private String email;
    private LocalDateTime createdAt;
}
