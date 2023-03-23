package com.vape.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.google.gson.Gson;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String avatar;
    private int quantity;
    private int price;
    private String description;
    @JsonBackReference
    @OneToMany(cascade = CascadeType.ALL)
    private List<ProductDetail> productDetails;

    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "id", insertable=false, updatable=false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category category;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL)
    private List<Image> images;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL)
    private List<Vote> votes;

    public Product() {
    }

    public Product(Long id, String name, String avatar, int quantity, int price, String description) {
        this.id = id;
        this.name = name;
        this.avatar = avatar;
        this.quantity = quantity;
        this.price = price;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
