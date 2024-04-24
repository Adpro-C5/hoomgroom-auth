package id.ac.ui.cs.advprog.auth.service.profile;

import id.ac.ui.cs.advprog.auth.dto.profile.ProfileRequest;
import id.ac.ui.cs.advprog.auth.model.User;
import id.ac.ui.cs.advprog.auth.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminProfileTemplate extends ProfileTemplate {
    private final UserRepository userRepository;

    @Override
    public void responseBuilder(User user, ProfileRequest request) {
        relatedUser = userRepository.findByUsername(user.getUsername()).orElseThrow();
        relatedUser.setName(request.getName());
        this.userRepository.save(relatedUser);
    }
}