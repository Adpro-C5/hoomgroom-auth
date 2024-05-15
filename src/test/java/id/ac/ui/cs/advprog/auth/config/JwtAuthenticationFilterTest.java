package id.ac.ui.cs.advprog.auth.config;

import id.ac.ui.cs.advprog.auth.exceptions.auth.VerificationFailedException;
import id.ac.ui.cs.advprog.auth.service.auth.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class JwtAuthenticationFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private FilterChain filterChain;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private MockMvc mockMvc;

    private static final String VALID_TOKEN = "Bearer validtoken";
    private static final String USERNAME = "user";

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new JwtAuthenticationFilter(jwtService, userDetailsService)).build();
    }

    @Test
    public void testNoAuthorizationHeader() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(null);
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        verify(filterChain).doFilter(request, response);
    }

    @Test
    public void testInvalidAuthorizationScheme() throws Exception {
        when(request.getHeader("Authorization")).thenReturn("Basic somecredentials");
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        verify(filterChain).doFilter(request, response);
    }

    @Test
    public void testValidTokenAndAuthentication() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(VALID_TOKEN);
        when(jwtService.extractUsername("validtoken")).thenReturn(USERNAME);
        when(userDetailsService.loadUserByUsername(USERNAME)).thenReturn(new User(USERNAME, "", new ArrayList<>()));
        when(jwtService.isTokenValid("validtoken", new User(USERNAME, "", new ArrayList<>()))).thenReturn(true);

        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
        verify(jwtService).extractUsername("validtoken");
    }

    @Test
    public void testTokenExpiredException() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(VALID_TOKEN);
        when(jwtService.extractUsername("validtoken")).thenThrow(new ExpiredJwtException(null, null, "expired"));

        assertThrows(VerificationFailedException.class, () -> {
            jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        });
    }

    @Test
    public void testSignatureException() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(VALID_TOKEN);
        when(jwtService.extractUsername("validtoken")).thenThrow(new SignatureException("signature failed"));

        assertThrows(VerificationFailedException.class, () -> {
            jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        });
    }

    @Test
    public void testGeneralException() throws Exception {
        when(request.getHeader("Authorization")).thenReturn(VALID_TOKEN);
        when(jwtService.extractUsername("validtoken")).thenThrow(new RuntimeException("Some error"));

        assertThrows(VerificationFailedException.class, () -> {
            jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);
        });
    }
}