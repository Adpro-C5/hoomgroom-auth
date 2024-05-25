package id.ac.ui.cs.advprog.auth.model;

public class ProfileAddressUpdateRequest {
    private String newAddress;

    public ProfileAddressUpdateRequest() {}

    public ProfileAddressUpdateRequest(String newAddress) {
        this.newAddress = newAddress;
    }

    public String getNewAddress() {
        return newAddress;
    }
}