package id.ac.ui.cs.advprog.auth.controller;

import id.ac.ui.cs.advprog.auth.model.User;
import id.ac.ui.cs.advprog.auth.repository.UserRepository;
import id.ac.ui.cs.advprog.auth.dto.profile.ProfileRequest;
import id.ac.ui.cs.advprog.auth.dto.profile.ProfileResponse;
import id.ac.ui.cs.advprog.auth.service.profile.AdminProfileTemplate;
import id.ac.ui.cs.advprog.auth.service.profile.BuyerProfileTemplate;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    @Autowired
    private Executor asyncTaskExecutor;

    @PutMapping("")
    @PreAuthorize("hasAuthority('verify:read')")
    public CompletableFuture<ResponseEntity<ProfileResponse>> editProfile (
            @RequestBody ProfileRequest request
    ) {
        return CompletableFuture.supplyAsync(() -> {
            ProfileResponse response;
            var currentUser = getCurrentUser();
            
            if (currentUser.getRole().equals("ADMIN")) {
                response = new AdminProfileTemplate(userRepository).process(currentUser, request);
            } else {
                response = new BuyerProfileTemplate(userRepository).process(currentUser, request);
            }

            return ResponseEntity.ok(response);
        }, asyncTaskExecutor);
    }

    private static User getCurrentUser() {
        return ((User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal());
    }
}