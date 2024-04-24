package id.ac.ui.cs.advprog.auth.exceptions;

import id.ac.ui.cs.advprog.auth.exceptions.auth.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final String ZONE_ID = "Asia/Jakarta";

    @ExceptionHandler(value = {UserAlreadyExistException.class})
    public ResponseEntity<Object> userExist() {
        ErrorTemplate baseException = new ErrorTemplate(
                "User with the same username already exist",
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of(ZONE_ID))
        );

        return new ResponseEntity<>(baseException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {UsernameIsEmptyException.class})
    public ResponseEntity<Object> usernameIsEmpty() {
        ErrorTemplate baseException = new ErrorTemplate(
                "Input username is empty, please input username",
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of(ZONE_ID))
        );

        return new ResponseEntity<>(baseException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {PasswordIsEmptyException.class})
    public ResponseEntity<Object> passwordIsEmpty() {
        ErrorTemplate baseException = new ErrorTemplate(
                "Input password is empty, please input password",
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of(ZONE_ID))
        );

        return new ResponseEntity<>(baseException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MinimumPasswordException.class})
    public ResponseEntity<Object> minimumPassword() {
        ErrorTemplate baseException = new ErrorTemplate(
                "Password must be at least 8 characters",
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of(ZONE_ID))
        );

        return new ResponseEntity<>(baseException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {UserDoesNotExistException.class})
    public ResponseEntity<Object> userOrReportNotExist(Exception exception) {
        ErrorTemplate baseException = new ErrorTemplate(
                exception.getMessage(),
                HttpStatus.NOT_FOUND,
                ZonedDateTime.now(ZoneId.of(ZONE_ID))
        );

        return new ResponseEntity<>(baseException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {
            UserIsAdministratorException.class,
            UserHasBeenBlockedException.class,
            IncorrectCredentialException.class,
            InvalidPasswordException.class})
    public ResponseEntity<Object> exceptionBadRequest(Exception exception) {
        ErrorTemplate baseException = new ErrorTemplate(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of(ZONE_ID))
        );

        return new ResponseEntity<>(baseException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {InvalidTokenException.class})
    public ResponseEntity<Object> invalidToken() {
        ErrorTemplate baseException = new ErrorTemplate(
                "Invalid token",
                HttpStatus.BAD_REQUEST,
                ZonedDateTime.now(ZoneId.of(ZONE_ID))
        );

        return new ResponseEntity<>(baseException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {
        VerificationFailedException.class,
        AuthenticationException.class,
    })
    public ResponseEntity<Object> unAuthorizedException(Exception exc) {
        ErrorTemplate baseException = new ErrorTemplate(
                exc.getMessage(),
                HttpStatus.UNAUTHORIZED,
                ZonedDateTime.now(ZoneId.of(ZONE_ID))
        );

        return new ResponseEntity<>(baseException, HttpStatus.UNAUTHORIZED);
    }
}