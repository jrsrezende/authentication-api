package br.com.jrsr.authenticationapi.repositories;

import br.com.jrsr.authenticationapi.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query("SELECT u FROM User u " +
            "WHERE u.email = :email")
    User findByEmail(@Param("email") String email);

    @Query("SELECT u FROM User u " +
            "WHERE u.email = :email " +
            "AND u.password = :password")
    User findByEmailAndPassword(@Param("email") String email, @Param("password") String password);
}
