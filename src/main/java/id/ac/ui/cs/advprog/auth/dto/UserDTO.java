package id.ac.ui.cs.advprog.auth.dto;

import java.util.Date;

import lombok.Generated;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Generated
public class UserDTO {
    private String name;
    private Date birthdate;
    private String gender;
    private String username;
    private String email;
    private String role;

    public UserDTO(String name, Date birthdate, String gender, String username, String email, String role) {
        this.name = name;
        this.birthdate = birthdate;
        this.gender = gender;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public Date getBirthdate() {
        return birthdate;
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

    public String getRole() {
        return role;
    }
}