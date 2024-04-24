package id.ac.ui.cs.advprog.auth.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class UserTest {
    User user;

    @BeforeEach
    void setUp() {
        user = User.builder()
                   .id(1)
                   .username("testUser")
                   .password("password")
                   .role("ADMIN")
                   .active(true)
                   .birthdate(new Date())
                   .email("test@example.com")
                   .gender("Male")
                   .build();
    }

    @Test
    void testGetAuthoritiesAsAdmin() {
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        assertTrue(authorities.stream().anyMatch(a -> a.getAuthority().equals("product:create")));
        assertTrue(authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN")));
    }

    @Test
    void testGetAuthoritiesAsBuyer() {
        user.setRole("BUYER");
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        assertFalse(authorities.stream().anyMatch(a -> a.getAuthority().equals("product:create")));
        assertTrue(authorities.stream().anyMatch(a -> a.getAuthority().equals("product:read")));
        assertTrue(authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_BUYER")));
    }

    @Test
    void testGetAuthoritiesAsGuest() {
        user.setRole("GUEST");
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        assertFalse(authorities.stream().anyMatch(a -> a.getAuthority().equals("product:create")));
        assertTrue(authorities.stream().anyMatch(a -> a.getAuthority().equals("product:read")));
        assertTrue(authorities.stream().anyMatch(a -> a.getAuthority().equals("ROLE_GUEST")));
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
    void testGetUsername() {
        assertEquals("testUser", user.getUsername());
    }

    @Test
    void testGetPassword() {
        assertEquals("password", user.getPassword());
    }
}