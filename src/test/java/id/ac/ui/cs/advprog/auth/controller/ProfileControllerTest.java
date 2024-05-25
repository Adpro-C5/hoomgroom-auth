package id.ac.ui.cs.advprog.auth.controller;

import id.ac.ui.cs.advprog.auth.service.ProfileService;
import id.ac.ui.cs.advprog.auth.model.ProfileResponse;
import id.ac.ui.cs.advprog.auth.model.ProfileAddressUpdateRequest;
import id.ac.ui.cs.advprog.auth.model.ProfileBalanceUpdateRequest;
import id.ac.ui.cs.advprog.auth.model.ProfilePasswordUpdateRequest;

import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class ProfileControllerTest {

    @InjectMocks
    private ProfileController profileController;

    @Mock
    private ProfileService profileService;

    @Mock
    private HttpServletResponse response;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProfile() {
        // Arrange
        String token = "sample-token";
        ProfileResponse profileResponse = new ProfileResponse("User profile retrieved successfully", 1, "John Doe", LocalDate.of(1990, 1, 1), "Male", "johndoe", "johndoe@example.com", "123 Main St", 1000L);
        when(profileService.getProfile(anyString())).thenReturn(ResponseEntity.ok(profileResponse));

        // Act
        ResponseEntity<ProfileResponse> responseEntity = profileController.getProfile(token);

        // Assert
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(profileResponse, responseEntity.getBody());
    }

    @Test
    void testUpdatePassword() {
        // Arrange
        String token = "sample-token";
        ProfilePasswordUpdateRequest request = new ProfilePasswordUpdateRequest("oldPassword", "newPassword");
        ProfileResponse profileResponse = new ProfileResponse("Password updated successfully", null, null, null, null, null, null, null, null);
        when(profileService.updatePassword(anyString(), anyString(), anyString())).thenReturn(ResponseEntity.ok(profileResponse));

        // Act
        ResponseEntity<ProfileResponse> responseEntity = profileController.updatePassword(token, request);

        // Assert
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(profileResponse, responseEntity.getBody());
    }

    @Test
    void testUpdateAddress() {
        // Arrange
        String token = "sample-token";
        ProfileAddressUpdateRequest request = new ProfileAddressUpdateRequest("new address");
        ProfileResponse profileResponse = new ProfileResponse("Address updated successfully", null, null, null, null, null, null, null, null);
        when(profileService.updateAddress(anyString(), anyString())).thenReturn(ResponseEntity.ok(profileResponse));

        // Act
        ResponseEntity<ProfileResponse> responseEntity = profileController.updateAddress(token, request);

        // Assert
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(profileResponse, responseEntity.getBody());
    }

    @Test
    void testUpdateBalance() {
        // Arrange
        String token = "sample-token";
        ProfileBalanceUpdateRequest request = new ProfileBalanceUpdateRequest(1, 500L);
        ProfileResponse profileResponse = new ProfileResponse("Balance updated successfully", null, null, null, null, null, null, null, null);
        when(profileService.updateBalance(anyString(), any(Integer.class), any(Long.class))).thenReturn(ResponseEntity.ok(profileResponse));

        // Act
        ResponseEntity<ProfileResponse> responseEntity = profileController.updateBalance(token, request);

        // Assert
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(profileResponse, responseEntity.getBody());
    }

    @Test
    void testDeleteProfile() {
        // Arrange
        String token = "sample-token";
        ProfileResponse profileResponse = new ProfileResponse("User profile deleted successfully", null, null, null, null, null, null, null, null);
        when(profileService.deleteProfile(anyString())).thenReturn(ResponseEntity.ok(profileResponse));

        // Act
        ResponseEntity<ProfileResponse> responseEntity = profileController.deleteProfile(token, response);

        // Assert
        assertEquals(200, responseEntity.getStatusCode().value());
        assertEquals(profileResponse, responseEntity.getBody());
    }
}