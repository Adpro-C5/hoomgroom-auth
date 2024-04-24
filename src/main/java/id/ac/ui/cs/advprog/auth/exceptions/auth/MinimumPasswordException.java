package id.ac.ui.cs.advprog.auth.exceptions.auth;

public class MinimumPasswordException extends RuntimeException {
    public MinimumPasswordException() {
        super("Password minimum has 8 characters");
    }
}