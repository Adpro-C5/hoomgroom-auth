package id.ac.ui.cs.advprog.auth.repository;

import id.ac.ui.cs.advprog.auth.model.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
}