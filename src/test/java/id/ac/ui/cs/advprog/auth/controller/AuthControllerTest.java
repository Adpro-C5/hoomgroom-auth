package id.ac.ui.cs.advprog.auth.controller;

import id.ac.ui.cs.advprog.auth.model.User;
import id.ac.ui.cs.advprog.auth.model.AuthResponse;
import id.ac.ui.cs.advprog.auth.service.AuthService;

import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthService authService;

    @Mock
    private HttpServletResponse response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRegister() {
        // Arrange
        User user = new User();
        AuthResponse authResponse = new AuthResponse(null, "User registered successfully");
        when(authService.register(any(User.class))).thenReturn(ResponseEntity.ok(authResponse));

        // Act
        ResponseEntity<AuthResponse> responseEntity = authController.register(user);

        // Assert
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(authResponse, responseEntity.getBody());
    }

    @Test
    void testLogin() {
        // Arrange
        User user = new User();
        AuthResponse authResponse = new AuthResponse("token", "User authenticated successfully");
        when(authService.authenticate(any(User.class), any(HttpServletResponse.class))).thenReturn(ResponseEntity.ok(authResponse));

        // Act
        ResponseEntity<AuthResponse> responseEntity = authController.login(user, response);

        // Assert
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(authResponse, responseEntity.getBody());
    }
}