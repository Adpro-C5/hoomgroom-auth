package id.ac.ui.cs.advprog.auth.model;

import id.ac.ui.cs.advprog.auth.enums.Role;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class TokenTest {
    private Token token;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1);
        user.setFullName("John Doe");
        user.setBirthDate(LocalDate.of(1990, 1, 1));
        user.setGender("Male");
        user.setUsername("johndoe");
        user.setEmail("johndoe@example.com");
        user.setAddress("123 Main St");
        user.setBalance(1000L);
        user.setPassword("password123");
        user.setRole(Role.BUYER);

        token = new Token();
        token.setId(1);
        token.setToken("sample-token");
        token.setLoggedOut(false);
        token.setUser(user);
    }

    @Test
    void testGetId() {
        assertEquals(1, token.getId());
    }

    @Test
    void testSetId() {
        token.setId(2);
        assertEquals(2, token.getId());
    }

    @Test
    void testGetToken() {
        assertEquals("sample-token", token.getToken());
    }

    @Test
    void testSetToken() {
        token.setToken("new-token");
        assertEquals("new-token", token.getToken());
    }

    @Test
    void testIsLoggedOut() {
        assertFalse(token.isLoggedOut());
    }

    @Test
    void testSetLoggedOut() {
        token.setLoggedOut(true);
        assertTrue(token.isLoggedOut());
    }

    @Test
    void testGetUser() {
        assertEquals(user, token.getUser());
    }

    @Test
    void testSetUser() {
        User newUser = new User();
        newUser.setId(2);
        newUser.setFullName("Jane Doe");
        newUser.setBirthDate(LocalDate.of(1991, 2, 2));
        newUser.setGender("Female");
        newUser.setUsername("janedoe");
        newUser.setEmail("janedoe@example.com");
        newUser.setAddress("456 Elm St");
        newUser.setBalance(2000L);
        newUser.setPassword("newpassword123");
        newUser.setRole(Role.ADMIN);

        token.setUser(newUser);
        assertEquals(newUser, token.getUser());
    }
}