package id.ac.ui.cs.advprog.auth.service.auth;

import id.ac.ui.cs.advprog.auth.model.User;
import id.ac.ui.cs.advprog.auth.repository.UserRepository;
import id.ac.ui.cs.advprog.auth.dto.auth.LoginRequest;
import id.ac.ui.cs.advprog.auth.dto.auth.LoginResponse;
import id.ac.ui.cs.advprog.auth.exceptions.auth.IncorrectCredentialException;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class Login implements AuthenticationFactory<LoginRequest, LoginResponse> {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;
    @Override
    public LoginResponse responseBuilder(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
            var user = generateUser(request);
            var jwtToken = jwtService.generateToken(user);
            return LoginResponse.builder().message("Sign in successful").token(jwtToken).build();
        } catch ( AuthenticationException error ) {
            throw new IncorrectCredentialException("The credential you provided was incorrect.");
        }
    }

    @Override
    public User generateUser(LoginRequest request) {
        return userRepository.findByUsername(request.getUsername()).orElseThrow();
    }
}