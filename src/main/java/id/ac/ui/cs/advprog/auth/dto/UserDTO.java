package id.ac.ui.cs.advprog.auth.dto;

import id.ac.ui.cs.advprog.auth.enums.Gender;
import id.ac.ui.cs.advprog.auth.enums.UserRole;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private String fullName;
    private LocalDate birthdate;
    private Gender gender;
    private String username;
    private String email;
    private UserRole role;
    private double walletBalance;
}