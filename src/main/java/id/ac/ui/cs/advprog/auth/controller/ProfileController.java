package id.ac.ui.cs.advprog.auth.controller;

import id.ac.ui.cs.advprog.auth.service.ProfileService;
import id.ac.ui.cs.advprog.auth.model.ProfileResponse;
import id.ac.ui.cs.advprog.auth.model.ProfileAddressUpdateRequest;
import id.ac.ui.cs.advprog.auth.model.ProfileBalanceUpdateRequest;
import id.ac.ui.cs.advprog.auth.model.ProfilePasswordUpdateRequest;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @GetMapping("/profile")
    public ResponseEntity<ProfileResponse> getProfile(@CookieValue(value = "jwt") String token) {
        return profileService.getProfile(token);
    }

    @PutMapping("/profile/password")
    public ResponseEntity<ProfileResponse> updatePassword(@CookieValue(value = "jwt") String token, @RequestBody ProfilePasswordUpdateRequest request) {
        return profileService.updatePassword(token, request.getOldPassword(), request.getNewPassword());
    }

    @PutMapping("/profile/address")
    public ResponseEntity<ProfileResponse> updateAddress(@CookieValue(value = "jwt") String token, @RequestBody ProfileAddressUpdateRequest request) {
        return profileService.updateAddress(token, request.getNewAddress());
    }

    @PutMapping("/profile/balance")
    public ResponseEntity<ProfileResponse> updateBalance(@CookieValue(value = "jwt") String token, @RequestBody ProfileBalanceUpdateRequest request) {
        return profileService.updateBalance(token, request.getUserId(), request.getAddedBalance());
    }

    @DeleteMapping("/profile/delete")
    public ResponseEntity<ProfileResponse> deleteProfile(@CookieValue(value = "jwt") String token, HttpServletResponse response) {
        response.addHeader("Set-Cookie", "jwt=; HttpOnly; SameSite=None; Path=/; Max-Age=0");
        return profileService.deleteProfile(token);
    }
}