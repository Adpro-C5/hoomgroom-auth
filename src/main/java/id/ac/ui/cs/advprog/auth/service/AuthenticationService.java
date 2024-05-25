package id.ac.ui.cs.advprog.auth.service;

import id.ac.ui.cs.advprog.auth.enums.Gender;
import id.ac.ui.cs.advprog.auth.enums.UserRole;
import id.ac.ui.cs.advprog.auth.enums.TokenType;

import id.ac.ui.cs.advprog.auth.model.User;
import id.ac.ui.cs.advprog.auth.model.Token;
import id.ac.ui.cs.advprog.auth.model.UserBuilder;
import id.ac.ui.cs.advprog.auth.dto.UserDTO;
import id.ac.ui.cs.advprog.auth.dto.auth.LoginRequest;
import id.ac.ui.cs.advprog.auth.dto.auth.LoginResponse;
import id.ac.ui.cs.advprog.auth.dto.auth.RegisterRequest;
import id.ac.ui.cs.advprog.auth.repository.UserRepository;
import id.ac.ui.cs.advprog.auth.repository.TokenRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public CompletableFuture<Void> register(RegisterRequest request) {
        return CompletableFuture.runAsync(() -> {
            User user = new UserBuilder()
                    .fullName(request.getFullName())
                    .birthdate(LocalDate.parse(request.getBirthdate(), DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .gender(Gender.valueOf(request.getGender()))
                    .username(request.getUsername())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(UserRole.valueOf(request.getRole()))
                    .build();
            repository.save(user);
        });
    }

    @Transactional
    public CompletableFuture<LoginResponse> login(LoginRequest request) {
        return CompletableFuture.supplyAsync(() -> {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );

            User user = repository.findByEmail(request.getEmail()).orElseThrow();
            String jwtToken = jwtService.generateToken(user);
            revokeAllUserTokens(user);
            saveUserToken(user, jwtToken);

            return LoginResponse.builder()
                    .token(jwtToken)
                    .userDTO(convertToUserData(user))
                    .build();
        });
    }

    private void saveUserToken(User user, String jwtToken) {
        Token token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserToken = tokenRepository.findAllValidTokensByUserEmail(user.getEmail());

        if (validUserToken.isEmpty())
            return;

        validUserToken.forEach(t -> {
            t.setExpired(true);
            t.setRevoked(true);
        });

        tokenRepository.saveAll(validUserToken);
    }

    private UserDTO convertToUserData(User user) {
        return UserDTO.builder()
                .fullName(user.getFullName())
                .birthdate(user.getBirthdate())
                .gender(user.getGender())
                .username(user.getRealUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .walletBalance(user.getWalletBalance())
                .build();
    }
}