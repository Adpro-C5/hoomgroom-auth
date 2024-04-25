package id.ac.ui.cs.advprog.auth.exceptions;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;

public record ErrorTemplate(String responseMessage, HttpStatus responseCode, ZonedDateTime timestamp) {}