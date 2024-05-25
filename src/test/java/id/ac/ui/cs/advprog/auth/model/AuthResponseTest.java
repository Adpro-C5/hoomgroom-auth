package id.ac.ui.cs.advprog.auth.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AuthResponseTest {

    @Test
    void testAuthResponse() {
        String expectedToken = "testToken";
        String expectedMessage = "testMessage";

        AuthResponse authResponse = new AuthResponse(expectedToken, expectedMessage);

        assertEquals(expectedToken, authResponse.getToken());
        assertEquals(expectedMessage, authResponse.getMessage());
    }
}