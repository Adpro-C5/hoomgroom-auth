package id.ac.ui.cs.advprog.auth.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDate;
import java.util.Collection;

import id.ac.ui.cs.advprog.auth.enums.Gender;
import id.ac.ui.cs.advprog.auth.enums.TokenType;
import id.ac.ui.cs.advprog.auth.enums.UserRole;

import java.util.List;

class UserTest {
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setFullName("John Doe");
        user.setBirthdate(LocalDate.of(1990, 1, 1));
        user.setGender(Gender.MALE);
        user.setUsername("johndoe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password");
        user.setRole(UserRole.ADMIN);
        user.setWalletBalance(100.0);
    }

    @Test
    void testGettersAndSetters() {
        assertEquals("John Doe", user.getFullName());
        assertEquals(LocalDate.of(1990, 1, 1), user.getBirthdate());
        assertEquals(Gender.MALE, user.getGender());
        assertEquals("johndoe", user.getRealUsername());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("password", user.getPassword());
        assertEquals(UserRole.ADMIN, user.getRole());
        assertEquals(100.0, user.getWalletBalance());
    }

    @Test
    void testGetAuthorities() {
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority("ADMIN")));
    }

    @Test
    void testGetUsername() {
        assertEquals("john.doe@example.com", user.getUsername());
    }

    @Test
    void testIsAccountNonExpired() {
        assertTrue(user.isAccountNonExpired());
    }

    @Test
    void testIsAccountNonLocked() {
        assertTrue(user.isAccountNonLocked());
    }

    @Test
    void testIsCredentialsNonExpired() {
        assertTrue(user.isCredentialsNonExpired());
    }

    @Test
    void testIsEnabled() {
        assertTrue(user.isEnabled());
    }

    @Test
    void testUserBuilder() {
        User builtUser = new UserBuilder()
                .fullName("Jane Doe")
                .birthdate(LocalDate.of(1992, 2, 2))
                .gender(Gender.FEMALE)
                .username("janedoe")
                .email("jane.doe@example.com")
                .password("password123")
                .role(UserRole.PEMBELI)
                .walletBalance(200.0)
                .tokens(null)
                .build();

        assertEquals("Jane Doe", builtUser.getFullName());
        assertEquals(LocalDate.of(1992, 2, 2), builtUser.getBirthdate());
        assertEquals(Gender.FEMALE, builtUser.getGender());
        assertEquals("janedoe", builtUser.getRealUsername());
        assertEquals("jane.doe@example.com", builtUser.getEmail());
        assertEquals("password123", builtUser.getPassword());
        assertEquals(UserRole.PEMBELI, builtUser.getRole());
        assertEquals(200.0, builtUser.getWalletBalance());
        assertNull(builtUser.getTokens());
    }

    @Test
    void testCanEqual() {
        User user2 = new User();
        assertTrue(user.canEqual(user2));
        assertFalse(user.canEqual(new Object()));
    }

    @Test
    void testSetTokens() {
        Token token1 = new Token("token1", TokenType.BEARER, false, false, user);
        Token token2 = new Token("token2", TokenType.BEARER, false, false, user);
        user.setTokens(List.of(token1, token2));
        assertEquals(2, user.getTokens().size());
        assertTrue(user.getTokens().contains(token1));
        assertTrue(user.getTokens().contains(token2));
    }
}