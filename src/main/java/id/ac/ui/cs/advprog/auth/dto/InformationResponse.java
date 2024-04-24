package id.ac.ui.cs.advprog.auth.dto;

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
public class InformationResponse {
    private String message;
    private UserDTO user;
}