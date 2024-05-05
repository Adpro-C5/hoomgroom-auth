package id.ac.ui.cs.advprog.auth.service.profile;

import id.ac.ui.cs.advprog.auth.model.User;
import id.ac.ui.cs.advprog.auth.dto.profile.ProfileRequest;
import id.ac.ui.cs.advprog.auth.dto.profile.ProfileResponse;
import id.ac.ui.cs.advprog.auth.exceptions.auth.UserAlreadyExistException;
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
class BuyerProfileTemplateTest {

    @InjectMocks
    private BuyerProfileTemplate service;

    @Mock
    private UserRepository repository;

    private ProfileRequest profileRequest;
    private ProfileResponse response;
    private User user;

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
                .role("BUYER")
                .build();
    }

    @Test
    void whenEditingUserProfileShouldReturnMessage() {
        when(repository.findByUsername(profileRequest.getUsername())).thenReturn(Optional.empty());
        when(repository.findByEmail(profileRequest.getEmail())).thenReturn(Optional.empty());
        when(repository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(repository.save(any(User.class))).thenReturn(user);

        ProfileResponse result = service.process(user, profileRequest);

        verify(repository, atLeastOnce()).findByUsername(profileRequest.getUsername());
        verify(repository, atLeastOnce()).findByEmail(profileRequest.getEmail());
        verify(repository, atLeastOnce()).findByUsername(user.getUsername());
        verify(repository, atLeastOnce()).save(any(User.class));
        Assertions.assertEquals(response, result);
    }

    @Test
    void whenTryingToEditProfileToAlreadyUsedUsernameShouldThrowError() {
        ProfileRequest usedProfile = ProfileRequest.builder()
                .email("newUser@gmail.com")
                .name("New User")
                .username("caesartirex")
                .build();
        User anotherUsedProfile = User.builder()
                .email("newUsers@gmail.com")
                .name("New User")
                .role("BUYER")
                .active(true)
                .username("caesartirex")
                .build();
        repository.save(anotherUsedProfile);
        when(repository.findByUsername(any(String.class))).thenReturn(Optional.of(user));

        Assertions.assertThrows(UserAlreadyExistException.class, () -> {
            service.process(user, usedProfile);
        });

        verify(repository, atLeastOnce()).findByUsername(any(String.class));
    }

    @Test
    void whenTryingToEditProfileToAlreadyUsedEmailShouldThrowError() {
        ProfileRequest usedProfile = ProfileRequest.builder()
                .email("newUsers@gmail.com")
                .name("New User")
                .username("caesartirek")
                .build();
        User anotherUsedProfile = User.builder()
                .email("newUsers@gmail.com")
                .name("New User")
                .role("BUYER")
                .active(true)
                .username("caesartirex")
                .build();
        repository.save(anotherUsedProfile);
        when(repository.findByEmail(any(String.class))).thenReturn(Optional.of(user));

        Assertions.assertThrows(UserAlreadyExistException.class, () -> {
            service.process(user, usedProfile);
        });

        verify(repository, atLeastOnce()).findByEmail(any(String.class));
    }
}