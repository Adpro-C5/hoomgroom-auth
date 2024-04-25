package id.ac.ui.cs.advprog.auth.dto.auth;

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
public class LoginResponse {
    private String message;
    private String token;
}