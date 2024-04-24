package id.ac.ui.cs.advprog.auth.exceptions.auth;

public class IncorrectCredentialException extends RuntimeException {
    public IncorrectCredentialException (String message) {
        super(message);
    }
}