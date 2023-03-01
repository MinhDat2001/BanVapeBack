package com.vape.model;

public class Product {
    private Long id;
    private String name;
    private String avatar;
    private int quantity;
    private double price;
    private String description;

    public Product() {
    }

    public Product(Long id, String name, String avatar, int quantity, double price, String description) {
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

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
