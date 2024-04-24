package id.ac.ui.cs.advprog.auth.exceptions.auth;

public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException() {
        super("Invalid token");
    }
}