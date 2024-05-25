package id.ac.ui.cs.advprog.auth.model;

public class AuthResponse {
    private final String token;
    private final String message;

    public AuthResponse(String token, String message) {
        this.token = token;
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public String getMessage() {
        return message;
    }
}