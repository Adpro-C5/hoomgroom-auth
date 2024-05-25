package id.ac.ui.cs.advprog.auth.dto.auth;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import id.ac.ui.cs.advprog.auth.dto.UserDTO;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String token;
    private UserDTO userDTO;
}