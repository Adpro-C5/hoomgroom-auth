package id.ac.ui.cs.advprog.auth.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("ADMIN"),
    PEMBELI("PEMBELI");

    private final String value;

    private UserRole(String value) { 
        this.value = value; 
    }
}