package id.ac.ui.cs.advprog.auth.exceptions.auth;

public class UserDoesNotExistException extends RuntimeException {
    public UserDoesNotExistException(String username) {
        super("User with username " + username + " does not exist");
    }
}