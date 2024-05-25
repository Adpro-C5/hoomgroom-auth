package id.ac.ui.cs.advprog.auth.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProfilePasswordUpdateRequestTest {

    @Test
    void testProfilePasswordUpdateRequest() {
        String expectedOldPassword = "oldPassword";
        String expectedNewPassword = "newPassword";
        ProfilePasswordUpdateRequest profilePasswordUpdateRequest = new ProfilePasswordUpdateRequest(expectedOldPassword, expectedNewPassword);
        ProfilePasswordUpdateRequest profilePasswordUpdateRequestTest = new ProfilePasswordUpdateRequest();
        assertEquals(expectedOldPassword, profilePasswordUpdateRequest.getOldPassword());
        assertEquals(expectedNewPassword, profilePasswordUpdateRequest.getNewPassword());
        assertNotNull(profilePasswordUpdateRequestTest);
    }
}