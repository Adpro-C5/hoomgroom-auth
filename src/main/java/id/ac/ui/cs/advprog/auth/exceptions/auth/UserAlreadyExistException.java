package id.ac.ui.cs.advprog.auth.exceptions.auth;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String message) {
        super(message);
    }
}