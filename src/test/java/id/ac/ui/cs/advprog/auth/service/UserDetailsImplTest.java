package id.ac.ui.cs.advprog.auth.service;

import id.ac.ui.cs.advprog.auth.model.User;
import id.ac.ui.cs.advprog.auth.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDetailsImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsImpl userDetailsService;

    private User user;

    @BeforeEach
    void setup() {
        user = new User();
        user.setUsername("testuser");
        user.setPassword("password");
    }

    @Test
    void testLoadUserByUsernameSuccess() {
        // Arrange
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        // Act
        UserDetails userDetails = userDetailsService.loadUserByUsername("testuser");

        // Assert
        assertNotNull(userDetails);
        assertEquals(user.getUsername(), userDetails.getUsername());
        verify(userRepository, times(1)).findByUsername("testuser");
    }

    @Test
    void testLoadUserByUsernameNotFound() {
        // Arrange
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("unknownuser");
        });
        verify(userRepository, times(1)).findByUsername("unknownuser");
    }
}