package id.ac.ui.cs.advprog.auth.service;

import id.ac.ui.cs.advprog.auth.enums.Role;
import id.ac.ui.cs.advprog.auth.model.Token;
import id.ac.ui.cs.advprog.auth.model.User;
import id.ac.ui.cs.advprog.auth.model.AuthResponse;
import id.ac.ui.cs.advprog.auth.repository.UserRepository;

import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;

    private User user;
    private Token token;

    @BeforeEach
    void setup() {
        user = new User();
        user.setId(1);
        user.setFullName("John Doe");
        user.setBirthDate(LocalDate.of(1990, 1, 1));
        user.setGender("Male");
        user.setUsername("johndoe");
        user.setEmail("johndoe@example.com");
        user.setAddress("123 Main St");
        user.setBalance(1000L);
        user.setPassword("password123");
        user.setRole(Role.BUYER);

        token = new Token();
        token.setToken("sample-token");
        token.setUser(user);
        token.setLoggedOut(false);
    }

    @Test
    void testRegisterSuccess() {
        // Arrange
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");

        // Act
        ResponseEntity<AuthResponse> responseEntity = authService.register(user);

        // Assert
        assertEquals(200, responseEntity.getStatusCode().value());
        AuthResponse body = responseEntity.getBody();
        assertNotNull(body);
        assertEquals("User registered successfully", body.getMessage());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testRegisterUsernameTaken() {
        // Arrange
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        // Act
        ResponseEntity<AuthResponse> responseEntity = authService.register(user);

        // Assert
        assertEquals(400, responseEntity.getStatusCode().value());
        AuthResponse body = responseEntity.getBody();
        assertNotNull(body);
        assertEquals("Username is already taken", body.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testRegisterPasswordTooShort() {
        // Arrange
        user.setPassword("short");

        // Act
        ResponseEntity<AuthResponse> responseEntity = authService.register(user);

        // Assert
        assertEquals(400, responseEntity.getStatusCode().value());
        AuthResponse body = responseEntity.getBody();
        assertNotNull(body);
        assertEquals("Password must be at least 8 characters", body.getMessage());
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testAuthenticateSuccess() {
        // Arrange
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(jwtService.saveUserToken(any(User.class))).thenReturn(token);

        HttpServletResponse response = mock(HttpServletResponse.class);

        // Act
        ResponseEntity<AuthResponse> responseEntity = authService.authenticate(user, response);

        // Assert
        assertEquals(200, responseEntity.getStatusCode().value());
        AuthResponse body = responseEntity.getBody();
        assertNotNull(body);
        assertEquals("User authenticated successfully", body.getMessage());
        assertEquals(token.getToken(), body.getToken());
        verify(jwtService, times(1)).revokeTokenByUser(user);
        verify(jwtService, times(1)).saveUserToken(user);
        verify(response, times(1)).addHeader(eq("Set-Cookie"), anyString());
    }

    @Test
    void testAuthenticateUserNotFound() {
        // Arrange
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        HttpServletResponse response = mock(HttpServletResponse.class);

        // Act
        ResponseEntity<AuthResponse> responseEntity = authService.authenticate(user, response);

        // Assert
        assertEquals(400, responseEntity.getStatusCode().value());
        AuthResponse body = responseEntity.getBody();
        assertNotNull(body);
        assertEquals("User not found", body.getMessage());
        verify(jwtService, never()).revokeTokenByUser(any(User.class));
        verify(jwtService, never()).saveUserToken(any(User.class));
    }
}