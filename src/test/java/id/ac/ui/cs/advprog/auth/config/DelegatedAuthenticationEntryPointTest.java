package id.ac.ui.cs.advprog.auth.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.core.AuthenticationException;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class DelegatedAuthenticationEntryPointTest {

    @InjectMocks
    private DelegatedAuthenticationEntryPoint authenticationEntryPoint;

    @Mock
    private HandlerExceptionResolver resolver;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private AuthenticationException authException;

    @Test
    void commence_shouldCallResolveException() throws IOException, ServletException {
        authenticationEntryPoint.commence(request, response, authException);
        verify(resolver).resolveException(eq(request), eq(response), isNull(), eq(authException));
    }
}