package id.ac.ui.cs.advprog.auth.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import id.ac.ui.cs.advprog.auth.dto.auth.LoginRequest;
import id.ac.ui.cs.advprog.auth.dto.auth.LoginResponse;
import id.ac.ui.cs.advprog.auth.dto.auth.RegisterRequest;
import id.ac.ui.cs.advprog.auth.service.AuthenticationService;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService service;

    @PostMapping("/register")
    public CompletableFuture<ResponseEntity<Void>> register(
            @RequestBody RegisterRequest request
    ) {
        return service.register(request)
                .thenApply(ResponseEntity::ok);
    }

    @PostMapping("/login")
    public CompletableFuture<ResponseEntity<LoginResponse>> login(
            @RequestBody LoginRequest request
    ) {
        return service.login(request)
                .thenApply(ResponseEntity::ok);
    }
}