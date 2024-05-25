package id.ac.ui.cs.advprog.auth.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import id.ac.ui.cs.advprog.auth.enums.TokenType;

class TokenTest {
    private Token token;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("john.doe@example.com");
        
        token = Token.builder()
                .token("sample-token")
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .user(user)
                .build();
    }

    @Test
    void testGettersAndSetters() {
        assertEquals("sample-token", token.getToken());
        assertEquals(TokenType.BEARER, token.getTokenType());
        assertFalse(token.isExpired());
        assertFalse(token.isRevoked());
        assertEquals(user, token.getUser());

        token.setToken("new-token");
        token.setTokenType(TokenType.BEARER);
        token.setExpired(true);
        token.setRevoked(true);
        token.setUser(null);

        assertEquals("new-token", token.getToken());
        assertEquals(TokenType.BEARER, token.getTokenType());
        assertTrue(token.isExpired());
        assertTrue(token.isRevoked());
        assertNull(token.getUser());
    }

    @Test
    void testNoArgsConstructor() {
        Token tokenNoArgs = new Token();
        assertNotNull(tokenNoArgs);
    }

    @Test
    void testAllArgsConstructor() {
        Token tokenAllArgs = new Token("all-token", TokenType.BEARER, true, true, user);
        assertEquals("all-token", tokenAllArgs.getToken());
        assertEquals(TokenType.BEARER, tokenAllArgs.getTokenType());
        assertTrue(tokenAllArgs.isExpired());
        assertTrue(tokenAllArgs.isRevoked());
        assertEquals(user, tokenAllArgs.getUser());
    }

    @Test
    void testBuilder() {
        Token builtToken = Token.builder()
                .token("built-token")
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .user(user)
                .build();

        assertEquals("built-token", builtToken.getToken());
        assertEquals(TokenType.BEARER, builtToken.getTokenType());
        assertFalse(builtToken.isExpired());
        assertFalse(builtToken.isRevoked());
        assertEquals(user, builtToken.getUser());
    }
}