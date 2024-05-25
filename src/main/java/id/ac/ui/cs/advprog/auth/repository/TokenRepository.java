package id.ac.ui.cs.advprog.auth.repository;

import id.ac.ui.cs.advprog.auth.model.User;
import id.ac.ui.cs.advprog.auth.model.Token;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, Integer> {
    Optional<Token> findByUser(User user);
    Optional<Token> findByToken(String token);
}