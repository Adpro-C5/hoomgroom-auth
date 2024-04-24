package id.ac.ui.cs.advprog.auth.exceptions.auth;

public class UsernameIsEmptyException extends RuntimeException {
    public UsernameIsEmptyException() {
        super("Username is empty");
    }
}