package id.ac.ui.cs.advprog.auth.dto.auth;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String fullName;
    private String birthdate;
    private String gender;
    private String username;
    private String email;
    private String password;
    private String role;
}