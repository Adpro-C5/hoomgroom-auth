package id.ac.ui.cs.advprog.auth.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProfileAddressUpdateRequestTest {

    @Test
    void testProfileAddressUpdateRequest() {
        String expectedStreet = "testStreet";
        ProfileAddressUpdateRequest profileAddressUpdateRequest = new ProfileAddressUpdateRequest(expectedStreet);
        ProfileAddressUpdateRequest profileAddressUpdateRequestTest = new ProfileAddressUpdateRequest();
        assertEquals(expectedStreet, profileAddressUpdateRequest.getNewAddress());
        assertNotNull(profileAddressUpdateRequestTest);
    }
}