package id.ac.ui.cs.advprog.auth.exceptions.auth;

public class UserIsAdministratorException extends RuntimeException {
    public UserIsAdministratorException(String username) {
        super("User with username " + username + " is administrator");
    }
}