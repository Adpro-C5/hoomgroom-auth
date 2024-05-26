package id.ac.ui.cs.advprog.auth.service;

import id.ac.ui.cs.advprog.auth.model.ProfileResponse;
import id.ac.ui.cs.advprog.auth.model.Token;
import id.ac.ui.cs.advprog.auth.model.User;
import id.ac.ui.cs.advprog.auth.repository.TokenRepository;
import id.ac.ui.cs.advprog.auth.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProfileServiceTest {

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ProfileService profileService;

    private Token token;
    private User user;

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

        token = new Token();
        token.setToken("sample-token");
        token.setUser(user);
        token.setLoggedOut(false);
    }

    @Test
    void testGetProfile() {
        // Arrange
        when(tokenRepository.findByToken(anyString())).thenReturn(Optional.of(token));

        // Act
        ResponseEntity<ProfileResponse> responseEntity = profileService.getProfile("sample-token");

        // Assert
        assertEquals(200, responseEntity.getStatusCode().value());
        ProfileResponse body = responseEntity.getBody();
        assertNotNull(body);
        assertEquals(user.getId(), body.getId());
    }

    @Test
    void testGetProfileInvalidToken() {
        // Arrange
        when(tokenRepository.findByToken(anyString())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<ProfileResponse> responseEntity = profileService.getProfile("invalid-token");

        // Assert
        assertEquals(400, responseEntity.getStatusCode().value());
        ProfileResponse body = responseEntity.getBody();
        assertNotNull(body);
        assertEquals("Invalid token", body.getMessage());
    }

    @Test
    void testUpdatePassword() {
        // Arrange
        when(tokenRepository.findByToken(anyString())).thenReturn(Optional.of(token));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);

        // Act
        ResponseEntity<ProfileResponse> responseEntity = profileService.updatePassword("sample-token", "password123", "newpassword123");

        // Assert
        assertEquals(200, responseEntity.getStatusCode().value());
        ProfileResponse body = responseEntity.getBody();
        assertNotNull(body);
        assertEquals("Password updated successfully", body.getMessage());
    }

    @Test
    void testUpdatePasswordInvalidToken() {
        // Arrange
        when(tokenRepository.findByToken(anyString())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<ProfileResponse> responseEntity = profileService.updatePassword("invalid-token", "password123", "newpassword123");

        // Assert
        assertEquals(400, responseEntity.getStatusCode().value());
        ProfileResponse body = responseEntity.getBody();
        assertNotNull(body);
        assertEquals("Invalid token", body.getMessage());
    }

    @Test
    void testUpdatePasswordIncorrectOldPassword() {
        // Arrange
        when(tokenRepository.findByToken(anyString())).thenReturn(Optional.of(token));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        // Act
        ResponseEntity<ProfileResponse> responseEntity = profileService.updatePassword("sample-token", "wrongpassword", "newpassword123");

        // Assert
        assertEquals(400, responseEntity.getStatusCode().value());
        ProfileResponse body = responseEntity.getBody();
        assertNotNull(body);
        assertEquals("Old password is incorrect", body.getMessage());
    }

    @Test
    void testUpdateAddress() {
        // Arrange
        when(tokenRepository.findByToken(anyString())).thenReturn(Optional.of(token));

        // Act
        ResponseEntity<ProfileResponse> responseEntity = profileService.updateAddress("sample-token", "456 New St");

        // Assert
        assertEquals(200, responseEntity.getStatusCode().value());
        ProfileResponse body = responseEntity.getBody();
        assertNotNull(body);
        assertEquals("Address updated successfully", body.getMessage());
    }

    @Test
    void testUpdateAddressInvalidToken() {
        // Arrange
        when(tokenRepository.findByToken(anyString())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<ProfileResponse> responseEntity = profileService.updateAddress("invalid-token", "456 New St");

        // Assert
        assertEquals(400, responseEntity.getStatusCode().value());
        ProfileResponse body = responseEntity.getBody();
        assertNotNull(body);
        assertEquals("Invalid token", body.getMessage());
    }

    @Test
    void testUpdateBalance() {
        // Arrange
        when(tokenRepository.findByToken(anyString())).thenReturn(Optional.of(token));
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));

        // Act
        ResponseEntity<ProfileResponse> responseEntity = profileService.updateBalance("sample-token", 1, 100000);

        // Assert
        assertEquals(200, responseEntity.getStatusCode().value());
        ProfileResponse body = responseEntity.getBody();
        assertNotNull(body);
        assertEquals("Balance updated successfully", body.getMessage());
    }

    @Test
    void testUpdateBalanceInvalidAmount() {
        // Arrange
        when(tokenRepository.findByToken(anyString())).thenReturn(Optional.of(token));
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(user));

        // Act
        ResponseEntity<ProfileResponse> responseEntity = profileService.updateBalance("sample-token", 1, 33000);

        // Assert
        assertEquals(400, responseEntity.getStatusCode().value());
        ProfileResponse body = responseEntity.getBody();
        assertNotNull(body);
        assertEquals("Top up balance amount is invalid", body.getMessage());
    }

    @Test
    void testUpdateBalanceInvalidToken() {
        // Arrange
        when(tokenRepository.findByToken(anyString())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<ProfileResponse> responseEntity = profileService.updateBalance("invalid-token", 1, 500L);

        // Assert
        assertEquals(400, responseEntity.getStatusCode().value());
        ProfileResponse body = responseEntity.getBody();
        assertNotNull(body);
        assertEquals("Invalid token", body.getMessage());
    }

    @Test
    void testUpdateBalanceUserNotFound() {
        // Arrange
        when(tokenRepository.findByToken(anyString())).thenReturn(Optional.of(token));
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<ProfileResponse> responseEntity = profileService.updateBalance("sample-token", 1, 500L);

        // Assert
        assertEquals(400, responseEntity.getStatusCode().value());
        ProfileResponse body = responseEntity.getBody();
        assertNotNull(body);
        assertEquals("User not found", body.getMessage());
    }

    @Test
    void testDeleteProfile() {
        // Arrange
        when(tokenRepository.findByToken(anyString())).thenReturn(Optional.of(token));

        // Act
        ResponseEntity<ProfileResponse> responseEntity = profileService.deleteProfile("sample-token");

        // Assert
        assertEquals(200, responseEntity.getStatusCode().value());
        ProfileResponse body = responseEntity.getBody();
        assertNotNull(body);
        assertEquals("User profile deleted successfully", body.getMessage());
    }

    @Test
    void testDeleteProfileInvalidToken() {
        // Arrange
        when(tokenRepository.findByToken(anyString())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<ProfileResponse> responseEntity = profileService.deleteProfile("invalid-token");

        // Assert
        assertEquals(400, responseEntity.getStatusCode().value());
        ProfileResponse body = responseEntity.getBody();
        assertNotNull(body);
        assertEquals("Invalid token", body.getMessage());
    }
}