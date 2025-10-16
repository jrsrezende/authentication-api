package br.com.jrsr.authenticationapi.services;

import br.com.jrsr.authenticationapi.dto.auth.AuthenticateUserRequestDTO;
import br.com.jrsr.authenticationapi.dto.auth.AuthenticateUserResponseDTO;
import br.com.jrsr.authenticationapi.dto.user.CreateUserRequestDTO;
import br.com.jrsr.authenticationapi.dto.user.CreateUserResponseDTO;
import br.com.jrsr.authenticationapi.domain.entities.User;
import br.com.jrsr.authenticationapi.exceptions.AccessDeniedException;
import br.com.jrsr.authenticationapi.exceptions.EmailAlreadyRegisteredException;
import br.com.jrsr.authenticationapi.helpers.CryptoHelper;
import br.com.jrsr.authenticationapi.helpers.JwtHelper;
import br.com.jrsr.authenticationapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repository;

    @Value("${jwt.key}")
    private String jwtKey;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public CreateUserResponseDTO createUser(CreateUserRequestDTO request) {
        if (repository.findByEmail(request.getEmail()).isPresent()) {
            throw new EmailAlreadyRegisteredException("Email already registered.");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(CryptoHelper.getSha256(request.getPassword()));

        repository.save(user);

        CreateUserResponseDTO response = new CreateUserResponseDTO();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setCreatedAt(user.getCreatedAt());

        return response;
    }

    public AuthenticateUserResponseDTO authenticateUser(AuthenticateUserRequestDTO request) {
        User user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new AccessDeniedException("Access denied. Incorrect email or password."));

        if (!user.getPassword().equals(CryptoHelper.getSha256(request.getPassword()))) {
            throw new AccessDeniedException("Access denied. Incorrect email or password.");
        }

        long expiration = 3600000L;

        String token = JwtHelper.generateToken(request.getEmail(), expiration, jwtKey);

        AuthenticateUserResponseDTO response = new AuthenticateUserResponseDTO();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setToken(token);

        return response;
    }
}
