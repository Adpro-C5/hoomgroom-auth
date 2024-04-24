package id.ac.ui.cs.advprog.auth.service.profile;

import id.ac.ui.cs.advprog.auth.model.User;
import id.ac.ui.cs.advprog.auth.dto.profile.ProfileRequest;
import id.ac.ui.cs.advprog.auth.dto.profile.ProfileResponse;

public abstract class ProfileTemplate {
    User relatedUser;

    public ProfileResponse process(User user, ProfileRequest request) {
        responseBuilder(user, request);
        return createResponse();
    }

    public ProfileResponse createResponse() {
        return ProfileResponse.builder().message("Successfuly update your profile!").build();
    }

    public abstract void responseBuilder(User user, ProfileRequest request);
}