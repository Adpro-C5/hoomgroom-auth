package id.ac.ui.cs.advprog.auth.model;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProfileResponseTest {

    @Test
    void testProfileResponse() {
        // Arrange
        String message = "User profile retrieved successfully";
        Integer id = 1;
        String fullName = "John Doe";
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        String gender = "Male";
        String username = "johndoe";
        String email = "johndoe@example.com";
        String address = "123 Main St";
        Long balance = 1000L;

        // Act
        ProfileResponse profileResponse = new ProfileResponse(message, id, fullName, birthDate, gender, username, email, address, balance);

        // Assert
        assertEquals(message, profileResponse.getMessage());
        assertEquals(id, profileResponse.getId());
        assertEquals(fullName, profileResponse.getFullName());
        assertEquals(birthDate, profileResponse.getBirthDate());
        assertEquals(gender, profileResponse.getGender());
        assertEquals(username, profileResponse.getUsername());
        assertEquals(email, profileResponse.getEmail());
        assertEquals(address, profileResponse.getAddress());
        assertEquals(balance, profileResponse.getBalance());
    }
}