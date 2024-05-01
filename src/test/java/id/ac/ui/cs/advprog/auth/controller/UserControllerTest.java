package id.ac.ui.cs.advprog.auth.controller;

import id.ac.ui.cs.advprog.auth.model.User;
import id.ac.ui.cs.advprog.auth.repository.UserRepository;
import id.ac.ui.cs.advprog.auth.dto.profile.ProfileRequest;
import id.ac.ui.cs.advprog.auth.dto.profile.ProfileResponse;

import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.test.context.TestSecurityContextHolder;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserController userController;

    @Test
    void editProfileTest() {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        TestSecurityContextHolder.setContext(securityContext);

        User user = User.builder()
                .name("Caesar Syahru Ramadhan")
                .username("caesartirek")
                .email("caesartirekn@gmail.com")
                .password("adproc5")
                .role("ADMIN")
                .build();

        when(authentication.getPrincipal()).thenReturn(user);

        ProfileRequest request = new ProfileRequest();
        request.setName("Caesar Syahru Ramadhan");

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        ResponseEntity<ProfileResponse> response = userController.editProfile(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Successfuly update your profile!", Objects.requireNonNull(response.getBody()).getMessage());
        verify(userRepository).save(any(User.class));
    }
}