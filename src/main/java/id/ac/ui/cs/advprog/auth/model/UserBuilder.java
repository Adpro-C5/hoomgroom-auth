package id.ac.ui.cs.advprog.auth.model;

import id.ac.ui.cs.advprog.auth.enums.Gender;
import id.ac.ui.cs.advprog.auth.enums.UserRole;

import java.time.LocalDate;
import java.util.List;

public class UserBuilder {
    String fullName;
    LocalDate birthdate;
    Gender gender;
    String username;
    String email;
    String password;
    UserRole role;
    double walletBalance;
    List<Token> tokens;

    public UserBuilder() {}

    public UserBuilder fullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public UserBuilder birthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    public UserBuilder gender (Gender gender) {
        this.gender = gender;
        return this;
    }

    public UserBuilder username(String username) {
        this.username = username;
        return this;
    }

    public UserBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder password(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder role(UserRole role) {
        this.role = role;
        return this;
    }

    public UserBuilder walletBalance(double walletBalance) {
        this.walletBalance = walletBalance;
        return this;
    }

    public UserBuilder tokens(List<Token> tokens) {
        this.tokens = tokens;
        return this;
    }

    public User build() {
        return new User(this);
    }
}