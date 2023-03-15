package com.vape.model.request;

public class UserRegisterRequest {
    private String email;
    private String name;
    private String phone;
    private String password;


    private String Address;

    public UserRegisterRequest() {
    }

    public UserRegisterRequest(String email, String name, String phone, String password, String address) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.password = password;
        Address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
}
