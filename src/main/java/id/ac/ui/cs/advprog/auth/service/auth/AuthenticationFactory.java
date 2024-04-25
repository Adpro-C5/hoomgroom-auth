package id.ac.ui.cs.advprog.auth.service.auth;

import id.ac.ui.cs.advprog.auth.model.User;

import org.springframework.stereotype.Service;

@Service
public interface AuthenticationFactory<T, R> {
    R responseBuilder(T request);
    User generateUser(T request);
}