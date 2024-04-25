package id.ac.ui.cs.advprog.auth.dto.profile;

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
public class ProfileRequest {
    private String name;
    private String email;
    private String username;
}