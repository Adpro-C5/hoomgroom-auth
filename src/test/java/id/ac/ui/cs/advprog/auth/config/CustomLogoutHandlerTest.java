package id.ac.ui.cs.advprog.auth.config;

import id.ac.ui.cs.advprog.auth.model.Token;
import id.ac.ui.cs.advprog.auth.model.User;
import id.ac.ui.cs.advprog.auth.repository.TokenRepository;
import id.ac.ui.cs.advprog.auth.service.JwtService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;

import java.util.Optional;

import static org.mockito.Mockito.*;

class CustomLogoutHandlerTest {

    @Mock
    private TokenRepository tokenRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private CustomLogoutHandler customLogoutHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogout_NoCookies() {
        when(request.getCookies()).thenReturn(null);

        customLogoutHandler.logout(request, response, authentication);

        verify(tokenRepository, never()).findByToken(anyString());
        verify(jwtService, never()).revokeTokenByUser(any(User.class));
    }

    @Test
    void testLogout_NoJwtCookie() {
        Cookie[] cookies = { new Cookie("other", "value") };
        when(request.getCookies()).thenReturn(cookies);

        customLogoutHandler.logout(request, response, authentication);

        verify(tokenRepository, never()).findByToken(anyString());
        verify(jwtService, never()).revokeTokenByUser(any(User.class));
    }

    @Test
    void testLogout_TokenNotFound() {
        Cookie[] cookies = { new Cookie("jwt", "sample-token") };
        when(request.getCookies()).thenReturn(cookies);
        when(tokenRepository.findByToken("sample-token")).thenReturn(Optional.empty());

        customLogoutHandler.logout(request, response, authentication);

        verify(tokenRepository, times(1)).findByToken("sample-token");
        verify(jwtService, never()).revokeTokenByUser(any(User.class));
    }

    @Test
    void testLogout_TokenFound() {
        Cookie[] cookies = { new Cookie("jwt", "sample-token") };
        when(request.getCookies()).thenReturn(cookies);
        Token token = new Token();
        User user = new User();
        token.setUser(user);
        when(tokenRepository.findByToken("sample-token")).thenReturn(Optional.of(token));

        customLogoutHandler.logout(request, response, authentication);

        verify(tokenRepository, times(1)).findByToken("sample-token");
        verify(jwtService, times(1)).revokeTokenByUser(user);
        verify(response, times(1)).addHeader("Set-Cookie", "jwt=; HttpOnly; SameSite=Lax; Path=/; Max-Age=0");
    }
}