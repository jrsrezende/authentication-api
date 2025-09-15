package br.com.jrsr.authenticationapi.service;

import br.com.jrsr.authenticationapi.dto.auth.AuthenticateUserRequestDTO;
import br.com.jrsr.authenticationapi.dto.auth.AuthenticateUserResponseDTO;
import br.com.jrsr.authenticationapi.dto.user.CreateUserRequestDTO;
import br.com.jrsr.authenticationapi.dto.user.CreateUserResponseDTO;
import br.com.jrsr.authenticationapi.domain.entities.User;
import br.com.jrsr.authenticationapi.exceptions.AccessDeniedException;
import br.com.jrsr.authenticationapi.exceptions.EmailAlreadyRegisteredException;
import br.com.jrsr.authenticationapi.helpers.CryptoHelper;
import br.com.jrsr.authenticationapi.helpers.JwtHelper;
import br.com.jrsr.authenticationapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    public CreateUserResponseDTO createUser(CreateUserRequestDTO request) {
        if (repository.findByEmail(request.getEmail()) != null) {
            throw new EmailAlreadyRegisteredException("Email already registered.");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(CryptoHelper.getSha256(request.getPassword()));
        user.setCreatedAt(LocalDateTime.now());

        repository.save(user);

        CreateUserResponseDTO response = new CreateUserResponseDTO();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setCreatedAt(user.getCreatedAt());

        return response;
    }

    public AuthenticateUserResponseDTO authenticateUser(AuthenticateUserRequestDTO request) {
        User user = repository.findByEmailAndPassword(request.getEmail(), CryptoHelper.getSha256(request.getPassword()));

        if (user == null) {
            throw new AccessDeniedException("Access denied. Invalid user.");
        }

        String token = JwtHelper.generateToken(request.getEmail(), 3600000L, "mysecretkeyfortheauthenticationapi");

        AuthenticateUserResponseDTO response = new AuthenticateUserResponseDTO();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setToken(token);
        response.setAccessedAt(LocalDateTime.now());
        response.setExpiresAt(LocalDateTime.now().plusSeconds(3600000L / 1000));

        return response;
    }
}
