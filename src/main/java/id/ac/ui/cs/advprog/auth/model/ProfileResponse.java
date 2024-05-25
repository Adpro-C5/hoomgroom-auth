package id.ac.ui.cs.advprog.auth.model;

import java.time.LocalDate;

public class ProfileResponse {
    private final String message;
    private final Integer id;
    private final String fullName;
    private final LocalDate birthDate;
    private final String gender;
    private final String username;
    private final String email;
    private final String address;
    private final Long balance;

    public ProfileResponse(String message, Integer id, String fullName, LocalDate birthDate, String gender, String username, String email, String address, Long balance) {
        this.message = message;
        this.id = id;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.username = username;
        this.email = email;
        this.address = address;
        this.balance = balance;
    }

    public String getMessage() {
        return message;
    }

    public Integer getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getGender() {
        return gender;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public Long getBalance() {
        return balance;
    }
}