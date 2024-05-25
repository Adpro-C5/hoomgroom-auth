package id.ac.ui.cs.advprog.auth.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProfileBalanceUpdateRequestTest {

    @Test
    void testProfileBalanceUpdateRequest() {
        long expectedBalance = 100000L;
        ProfileBalanceUpdateRequest profileBalanceUpdateRequest = new ProfileBalanceUpdateRequest(1, expectedBalance);
        ProfileBalanceUpdateRequest profileBalanceUpdateRequestTest = new ProfileBalanceUpdateRequest();

        assertNotNull(profileBalanceUpdateRequest);
        assertNotNull(profileBalanceUpdateRequestTest);
        assertEquals(1, profileBalanceUpdateRequest.getUserId());
        assertEquals(expectedBalance, profileBalanceUpdateRequest.getAddedBalance());
    }

}