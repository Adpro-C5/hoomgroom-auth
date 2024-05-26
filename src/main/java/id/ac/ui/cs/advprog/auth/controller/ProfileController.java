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
    public ResponseEntity<ProfileResponse> getProfile(@RequestHeader(value = "Authorization") String token) {
        return profileService.getProfile(token.substring(7));
    }

    @GetMapping("/profile/role")
    public ResponseEntity<String> getRole(@RequestHeader(value = "Authorization") String token) {
        return profileService.getRole(token.substring(7));
    }

    @PutMapping("/profile/password")
    public ResponseEntity<ProfileResponse> updatePassword(@RequestHeader(value = "Authorization") String token, @RequestBody ProfilePasswordUpdateRequest request) {
        return profileService.updatePassword(token.substring(7), request.getOldPassword(), request.getNewPassword());
    }

    @PutMapping("/profile/address")
    public ResponseEntity<ProfileResponse> updateAddress(@RequestHeader(value = "Authorization") String token, @RequestBody ProfileAddressUpdateRequest request) {
        return profileService.updateAddress(token.substring(7), request.getNewAddress());
    }

    @PutMapping("/profile/balance")
    public ResponseEntity<ProfileResponse> updateBalance(@RequestHeader(value = "Authorization") String token, @RequestBody ProfileBalanceUpdateRequest request) {
        return profileService.updateBalance(token.substring(7), request.getUserId(), request.getAddedBalance());
    }

    @PutMapping("/profile/balance/reduce")
    public ResponseEntity<ProfileResponse> reduceBalance(@RequestHeader(value = "Authorization") String token, @RequestBody ProfileBalanceUpdateRequest request) {
        return profileService.reduceBalance(token.substring(7), request.getUserId(), request.getAddedBalance());
    }

    @DeleteMapping("/profile/delete")
    public ResponseEntity<ProfileResponse> deleteProfile(@RequestHeader(value = "Authorization") String token, HttpServletResponse response) {
        return profileService.deleteProfile(token.substring(7));
    }
}