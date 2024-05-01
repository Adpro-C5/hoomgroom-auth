package id.ac.ui.cs.advprog.auth.controller;

import id.ac.ui.cs.advprog.auth.model.User;
import id.ac.ui.cs.advprog.auth.repository.UserRepository;
import id.ac.ui.cs.advprog.auth.dto.profile.ProfileRequest;
import id.ac.ui.cs.advprog.auth.dto.profile.ProfileResponse;
import id.ac.ui.cs.advprog.auth.service.profile.AdminProfileTemplate;
import id.ac.ui.cs.advprog.auth.service.profile.BuyerProfileTemplate;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;
    @PutMapping("")
    @PreAuthorize("hasAuthority('verify:read')")
    public ResponseEntity<ProfileResponse> editProfile (
            @RequestBody ProfileRequest request
    ) {
        ProfileResponse response;
        var currentUser = getCurrentUser();
        
        if (currentUser.getRole().equals("ADMIN")) {
            response = new AdminProfileTemplate(userRepository).process(currentUser, request);
        } else {
            response = new BuyerProfileTemplate(userRepository).process(currentUser, request);
        }

        return ResponseEntity.ok(response);
    }

    private static User getCurrentUser() {
        return ((User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal());
    }
}