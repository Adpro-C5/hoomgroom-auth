package id.ac.ui.cs.advprog.auth.service.auth;

import id.ac.ui.cs.advprog.auth.model.User;
import id.ac.ui.cs.advprog.auth.repository.UserRepository;
import id.ac.ui.cs.advprog.auth.dto.auth.RegisterRequest;
import id.ac.ui.cs.advprog.auth.dto.auth.RegisterResponse;
import id.ac.ui.cs.advprog.auth.exceptions.auth.UserAlreadyExistException;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
@RequiredArgsConstructor
public class Register implements AuthenticationFactory<RegisterRequest, RegisterResponse> {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public synchronized RegisterResponse responseBuilder(RegisterRequest request) {
        User checkUser = userRepository.findByUsername(request.getUsername()).orElse(null);

        checkIfUserExist(checkUser);
        checkUser = userRepository.findByEmail(request.getEmail()).orElse(null);
        checkIfUserExist(checkUser);
        generateUser(request);

        return RegisterResponse.builder().message("User successfuly registered!").build();
    }

    @Override
    public synchronized User generateUser(RegisterRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .active(true)
                .email(request.getEmail())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        return userRepository.save(user);
    }

    private synchronized void checkIfUserExist(User checkUser) throws UserAlreadyExistException {
        if (checkUser != null) {
            throw new UserAlreadyExistException("User already exists");
        }
    }
}