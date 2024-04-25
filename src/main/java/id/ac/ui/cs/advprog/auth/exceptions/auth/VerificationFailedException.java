package id.ac.ui.cs.advprog.auth.exceptions.auth;

public class VerificationFailedException extends RuntimeException {
    public VerificationFailedException(String message) {
        super(message);
    }
}