package id.ac.ui.cs.advprog.auth.service;

import id.ac.ui.cs.advprog.auth.model.Token;
import id.ac.ui.cs.advprog.auth.model.User;
import id.ac.ui.cs.advprog.auth.repository.TokenRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JwtServiceTest {

    @Mock
    private TokenRepository tokenRepository;

    @InjectMocks
    private JwtService jwtService;

    private Token token;
    private User user;

    @BeforeEach
    void setup() {
        ReflectionTestUtils.setField(jwtService, "SECRET_KEY", "hulgpGrrj3wvMVGLUzfG6N0A53WW7imKeZIRsARae6I5khcxMS5jND5eNYZB2LrB88I5LfsJc8zHBgQune7DKleAPy7rmezZwycAj5nmDPnle3pKLCMzKkV9yAO6BEOpYyharujzpR1QaRERlskZlAy10MC5W0ackIVxe2OSdECTfxqvxuw6mFlNKEvbHxohoRSasJiMBMLx6lQNbKlpF3ELsPNNjTkW57MegMrcPzPjums5w6dk6xfjJVbaL6eqtdR4SreQoNZex5OsPzk3CZN8jbZD7V2cRSyRAQCpbomA169eu5hIYAWvVlek67Xmk1Q63qUnL50Cw");
        user = new User();
        user.setUsername("testUser");
        token = jwtService.saveUserToken(user);
    }

    @Test
    void testExtractUsername() {
        String actualUsername = jwtService.extractUsername(token.getToken());
        assertEquals(user.getUsername(), actualUsername);
    }

    @Test
    void testIsValid() {
        when(tokenRepository.findByToken(token.getToken())).thenReturn(Optional.of(token));
        boolean isValid = jwtService.isValid(token.getToken(), user);
        assertTrue(isValid);
    }

    @Test
    void testIsValidTokenNotFound() {
        when(tokenRepository.findByToken(token.getToken())).thenReturn(Optional.empty());
        boolean isValid = jwtService.isValid(token.getToken(), user);
        assertFalse(isValid);
    }

    @Test
    void testIsNotValidUsernameMismatch() {
        when(tokenRepository.findByToken(token.getToken())).thenReturn(Optional.of(token));
        User anotherUser = new User();
        anotherUser.setUsername("testUser2");
        boolean isValid = jwtService.isValid(token.getToken(), anotherUser);
        assertFalse(isValid);
    }

    @Test
    void testIsNotValidTokenExpired() {
        when(tokenRepository.findByToken(token.getToken())).thenReturn(Optional.of(token));
        JwtService spyJwtService = spy(jwtService);
        doReturn(true).when(spyJwtService).isTokenExpired(anyString());

        boolean isValid = spyJwtService.isValid(token.getToken(), user);
        assertFalse(isValid);
    }

    @Test
    void testIsNotValidLoggedOut() {
        token.setLoggedOut(true);
        when(tokenRepository.findByToken(token.getToken())).thenReturn(Optional.of(token));
        boolean isValid = jwtService.isValid(token.getToken(), user);
        assertFalse(isValid);
    }

    @Test
    void testSaveUserToken() {
        Token token = jwtService.saveUserToken(user);
        verify(tokenRepository, times(1)).save(token);
    }

    @Test
    void testRevokeTokenByUser() {
        when(tokenRepository.findByUser(user)).thenReturn(Optional.of(token));
        Token revokedToken = jwtService.revokeTokenByUser(user);
        verify(tokenRepository, times(1)).delete(token);
        assertEquals(token, revokedToken);
    }

    @Test
    void testRevokeTokenByUserReturnsNull() {
        when(tokenRepository.findByUser(user)).thenReturn(Optional.empty());
        Token revokedToken = jwtService.revokeTokenByUser(user);
        assertNull(revokedToken);
    }
}