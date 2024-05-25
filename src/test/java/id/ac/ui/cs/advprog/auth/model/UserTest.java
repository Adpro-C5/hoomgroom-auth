package id.ac.ui.cs.advprog.auth.model;

import id.ac.ui.cs.advprog.auth.enums.Role;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDate;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
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
    }

    @Test
    void testGetId() {
        assertEquals(1, user.getId());
    }

    @Test
    void testSetId() {
        user.setId(2);
        assertEquals(2, user.getId());
    }

    @Test
    void testGetFullName() {
        assertEquals("John Doe", user.getFullName());
    }

    @Test
    void testSetFullName() {
        user.setFullName("Jane Doe");
        assertEquals("Jane Doe", user.getFullName());
    }

    @Test
    void testGetBirthDate() {
        assertEquals(LocalDate.of(1990, 1, 1), user.getBirthDate());
    }

    @Test
    void testSetBirthDate() {
        LocalDate newDate = LocalDate.of(1991, 2, 2);
        user.setBirthDate(newDate);
        assertEquals(newDate, user.getBirthDate());
    }

    @Test
    void testGetGender() {
        assertEquals("Male", user.getGender());
    }

    @Test
    void testSetGender() {
        user.setGender("Female");
        assertEquals("Female", user.getGender());
    }

    @Test
    void testGetUsername() {
        assertEquals("johndoe", user.getUsername());
    }

    @Test
    void testSetUsername() {
        user.setUsername("janedoe");
        assertEquals("janedoe", user.getUsername());
    }

    @Test
    void testGetEmail() {
        assertEquals("johndoe@example.com", user.getEmail());
    }

    @Test
    void testSetEmail() {
        user.setEmail("janedoe@example.com");
        assertEquals("janedoe@example.com", user.getEmail());
    }

    @Test
    void testGetAddress() {
        assertEquals("123 Main St", user.getAddress());
    }

    @Test
    void testSetAddress() {
        user.setAddress("456 Elm St");
        assertEquals("456 Elm St", user.getAddress());
    }

    @Test
    void testGetBalance() {
        assertEquals(1000L, user.getBalance());
    }

    @Test
    void testSetBalance() {
        user.setBalance(2000L);
        assertEquals(2000L, user.getBalance());
    }

    @Test
    void testGetPassword() {
        assertEquals("password123", user.getPassword());
    }

    @Test
    void testSetPassword() {
        user.setPassword("newpassword");
        assertEquals("newpassword", user.getPassword());
    }

    @Test
    void testGetRole() {
        assertEquals(Role.BUYER, user.getRole());
    }

    @Test
    void testSetRole() {
        user.setRole(Role.ADMIN);
        assertEquals(Role.ADMIN, user.getRole());
    }

    @Test
    void testGetAuthorities() {
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
        assertNotNull(authorities);
        assertEquals(1, authorities.size());
        assertTrue(authorities.contains(new SimpleGrantedAuthority(Role.BUYER.name())));
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
}