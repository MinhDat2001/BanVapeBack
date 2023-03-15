package com.vape.model.request;

public class UserIdentifyRequest {
    private String email;

    public UserIdentifyRequest(String email) {
        this.email = email;
    }

    public UserIdentifyRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
