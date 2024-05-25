package id.ac.ui.cs.advprog.auth.service;

import id.ac.ui.cs.advprog.auth.model.ProfileResponse;
import id.ac.ui.cs.advprog.auth.model.Token;
import id.ac.ui.cs.advprog.auth.model.User;
import id.ac.ui.cs.advprog.auth.repository.TokenRepository;
import id.ac.ui.cs.advprog.auth.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final String INVALID_MESSAGE = "Invalid token";

    public ResponseEntity<ProfileResponse> getProfile(String token) {
        Token storedToken = tokenRepository.findByToken(token).orElse(null);

        if (storedToken == null) {
            return ResponseEntity.badRequest().body(new ProfileResponse(INVALID_MESSAGE, null, null, null, null, null, null, null, null));
        }

        User user = storedToken.getUser();

        return ResponseEntity.ok(new ProfileResponse("User profile retrieved successfully", user.getId(), user.getFullName(), user.getBirthDate(), user.getGender(), user.getUsername(), user.getEmail(), user.getAddress(), user.getBalance()));
    }

    public ResponseEntity<ProfileResponse> updatePassword(String token, String oldPassword, String newPassword) {
        Token storedToken = tokenRepository.findByToken(token).orElse(null);

        if (storedToken == null) {
            return ResponseEntity.badRequest().body(new ProfileResponse(INVALID_MESSAGE, null, null, null, null, null, null, null, null));
        }

        User user = storedToken.getUser();
 
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return ResponseEntity.badRequest().body(new ProfileResponse("Old password is incorrect", null, null, null, null, null, null, null, null));
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return ResponseEntity.ok(new ProfileResponse("Password updated successfully", null, null, null, null, null, null, null, null));
    }

    public ResponseEntity<ProfileResponse> updateAddress(String token, String address) {
        Token storedToken = tokenRepository.findByToken(token).orElse(null);

        if (storedToken == null) {
            return ResponseEntity.badRequest().body(new ProfileResponse(INVALID_MESSAGE, null, null, null, null, null, null, null, null));
        }

        User user = storedToken.getUser();
        user.setAddress(address);
        userRepository.save(user);

        return ResponseEntity.ok(new ProfileResponse("Address updated successfully", null, null, null, null, null, null, null, null));
    }

    public ResponseEntity<ProfileResponse> updateBalance(String token, int userId, long balance) {
        Token storedToken = tokenRepository.findByToken(token).orElse(null);

        if (storedToken == null) {
            return ResponseEntity.badRequest().body(new ProfileResponse(INVALID_MESSAGE, null, null, null, null, null, null, null, null));
        }

        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body(new ProfileResponse("User not found", null, null, null, null, null, null, null, null));
        }

        user.setBalance(user.getBalance() + balance);
        userRepository.save(user);

        return ResponseEntity.ok(new ProfileResponse("Balance updated successfully", null, null, null, null, null, null, null, null));
    }

    public ResponseEntity<ProfileResponse> deleteProfile(String token) {
        Token storedToken = tokenRepository.findByToken(token).orElse(null);

        if (storedToken == null) {
            return ResponseEntity.badRequest().body(new ProfileResponse(INVALID_MESSAGE, null, null, null, null, null, null, null, null));
        }
        
        User user = storedToken.getUser();
        tokenRepository.delete(storedToken);
        userRepository.delete(user);

        return ResponseEntity.ok(new ProfileResponse("User profile deleted successfully", null, null, null, null, null, null, null, null));
    }
}