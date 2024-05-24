package id.ac.ui.cs.advprog.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.module.SimpleModule;

import id.ac.ui.cs.advprog.auth.model.User;
import id.ac.ui.cs.advprog.auth.service.auth.AuthenticationFactory;
import id.ac.ui.cs.advprog.auth.serializer.UserDTOSerializer;
import id.ac.ui.cs.advprog.auth.dto.UserDTO;
import id.ac.ui.cs.advprog.auth.dto.InformationResponse;
import id.ac.ui.cs.advprog.auth.dto.auth.RegisterRequest;
import id.ac.ui.cs.advprog.auth.dto.auth.RegisterResponse;
import id.ac.ui.cs.advprog.auth.dto.auth.LoginRequest;
import id.ac.ui.cs.advprog.auth.dto.auth.LoginResponse;
import id.ac.ui.cs.advprog.auth.exceptions.auth.VerificationFailedException;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationFactory<RegisterRequest, RegisterResponse> signUp;
    private final AuthenticationFactory<LoginRequest, LoginResponse> signIn;

    @Autowired
    private Executor asyncTaskExecutor;

    @PostMapping("/register")
    public CompletableFuture<ResponseEntity<RegisterResponse>> register (
            @RequestBody RegisterRequest request
    ) {
        return CompletableFuture.supplyAsync(() ->
            ResponseEntity.ok(signUp.responseBuilder(request)), asyncTaskExecutor);
    }

    @PostMapping("/login")
    public CompletableFuture<ResponseEntity<LoginResponse>> login (
            @RequestBody LoginRequest request
    ) {
        return CompletableFuture.supplyAsync(() ->
            ResponseEntity.ok(signIn.responseBuilder(request)), asyncTaskExecutor);
    }

    @GetMapping("/verify")
    @PreAuthorize("hasAuthority('verify:read')")
    public CompletableFuture<ResponseEntity<String>> verify() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                User user = getCurrentUser();
                UserDTO userDTO = new UserDTO(user.getName(), user.getBirthdate(), user.getGender(), user.getUsername(), user.getEmail(), user.getRole());
                InformationResponse response = InformationResponse.builder()
                        .message("Retrieve User Information Successful")
                        .user(userDTO)
                        .build();

                ObjectMapper objectMapper = new ObjectMapper();
                SimpleModule module = new SimpleModule();
                module.addSerializer(UserDTO.class, new UserDTOSerializer());
                objectMapper.registerModule(module);

                String jsonString = objectMapper.writeValueAsString(response);
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(jsonString);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("JSON processing failed.", e);
            } catch (RuntimeException err) {
                throw new VerificationFailedException("Your token has expired.");
            }
        }, asyncTaskExecutor);
    }

    private static User getCurrentUser() {
        return ((User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal());
    }
}