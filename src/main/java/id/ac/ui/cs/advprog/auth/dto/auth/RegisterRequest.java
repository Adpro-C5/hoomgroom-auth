package id.ac.ui.cs.advprog.auth.dto.auth;

import java.util.Date;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Generated;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Generated
public class RegisterRequest {
    private String name;
    private Date dateOfBirth;
    private String gender;
    private String username;
    private String email;
    private String password;
    private String role;
}