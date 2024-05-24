package id.ac.ui.cs.advprog.auth.service.profile;

import id.ac.ui.cs.advprog.auth.model.User;
import id.ac.ui.cs.advprog.auth.repository.UserRepository;
import id.ac.ui.cs.advprog.auth.dto.profile.ProfileRequest;
import id.ac.ui.cs.advprog.auth.exceptions.auth.UserAlreadyExistException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuyerProfileTemplate extends ProfileTemplate {
    private final UserRepository userRepository;

    @Override
    public void responseBuilder(User user, ProfileRequest request) {
        checkIfUsernameExist(request.getUsername(), user.getUsername());
        checkIfEmailExist(request.getEmail(), user.getEmail());
        relatedUser = userRepository.findByUsername(user.getUsername()).orElseThrow();
        relatedUser.setName(request.getName());
        relatedUser.setEmail(request.getEmail());
        relatedUser.setUsername(request.getUsername());
        this.userRepository.save(relatedUser);
    }

    private void checkIfUsernameExist(String username, String currentUsername) {
        User checkUser = userRepository.findByUsername(username).orElse(null);
        if (checkUser != null && !currentUsername.equals(username)) {
            throw new UserAlreadyExistException("Username is already used by another user");
        }
    }

    private void checkIfEmailExist(String email, String currentEmail) {
        User checkEmail = userRepository.findByEmail(email).orElse(null);

        if (checkEmail != null && !currentEmail.equals(email)) {
            throw new UserAlreadyExistException("Email is already used by another user");
        }
    }
}