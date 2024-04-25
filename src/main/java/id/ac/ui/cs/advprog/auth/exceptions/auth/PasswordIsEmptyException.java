package id.ac.ui.cs.advprog.auth.exceptions.auth;

public class PasswordIsEmptyException extends RuntimeException {
    public PasswordIsEmptyException() {
        super("Password is empty");
    }
}