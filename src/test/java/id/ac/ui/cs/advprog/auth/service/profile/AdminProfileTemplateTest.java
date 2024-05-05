package id.ac.ui.cs.advprog.auth.service.profile;

import id.ac.ui.cs.advprog.auth.model.User;
import id.ac.ui.cs.advprog.auth.dto.profile.ProfileRequest;
import id.ac.ui.cs.advprog.auth.dto.profile.ProfileResponse;
import id.ac.ui.cs.advprog.auth.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminProfileTemplateTest {

    @InjectMocks
    private AdminProfileTemplate service;

    @Mock
    private UserRepository repository;

    ProfileRequest profileRequest;
    ProfileResponse response;
    User user;

    ProfileRequest usedProfile;
    @BeforeEach
    void setUp() {
        response = ProfileResponse.builder().message("Successfuly update your profile!").build();
        
        profileRequest = ProfileRequest.builder()
                .email("newuser@gmail.com")
                .name("New User")
                .username("newUser")
                .build();

        user = User.builder()
                .name("Caesar Syahru Ramadhan")
                .username("caesartirek")
                .email("caesartirekn@gmail.com")
                .password("adproc5")
                .role("ADMIN")
                .build();
    }

    @Test
    void whenEditingUserProfileShouldReturnMessage() {
        when(repository.findByUsername(any(String.class))).thenReturn(Optional.ofNullable(user));
        when(repository.save(any(User.class))).thenReturn(user);

        User newUser = repository.save(user);
        ProfileResponse result = service.process(newUser, profileRequest);

        verify(repository, atLeastOnce()).findByUsername(any(String.class));
        Assertions.assertEquals(response, result);
    }
}