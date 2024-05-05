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

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationFactory<RegisterRequest, RegisterResponse> signUp;
    private final AuthenticationFactory<LoginRequest, LoginResponse> signIn;
    @PostMapping("/sign-up")
    public synchronized ResponseEntity<RegisterResponse> register (
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(signUp.responseBuilder(request));
    }

    @PostMapping("/sign-in")
    public ResponseEntity<LoginResponse> login (
            @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(signIn.responseBuilder(request));
    }

    @GetMapping("/verify")
    @PreAuthorize("hasAuthority('verify:read')")
    public ResponseEntity<String> verify () throws JsonProcessingException {
        try {
            var user = getCurrentUser();

            var userDTO = new UserDTO(user.getName(), user.getBirthdate(), user.getGender(), user.getUsername(), user.getEmail(), user.getRole());

            InformationResponse response = InformationResponse.builder()
                    .message("Retrieve User Information Successful")
                    .user(userDTO)
                    .build();

            var objectMapper = new ObjectMapper();
            var module = new SimpleModule();
            module.addSerializer(UserDTO.class, new UserDTOSerializer());
            objectMapper.registerModule(module);

            var jsonString = objectMapper.writeValueAsString(response);

            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(jsonString);
        } catch (RuntimeException err) {
            throw new VerificationFailedException("Your token has expired.");
        }
    }

    private static User getCurrentUser() {
        return ((User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal());
    }
}