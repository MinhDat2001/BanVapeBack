package com.vape.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.context.annotation.Primary;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String name;
    private String phone;
    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    private String role;
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL)
    private List<Vote> votes;
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL)
    private List<Bill> bills;

    @OneToOne
    @JoinColumn(name = "id")
    private Account account;

    @OneToMany(cascade = CascadeType.ALL)
    private List<Cart> carts;

    public User() {
    }

    public User(Long id, String email, String name, String phone, String role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.role = role;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
