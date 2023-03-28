package com.vape.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class ProductDetail implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @JsonProperty("quantity")
    private int quantity;

    @JsonProperty("avatar")
    private String avatar;

    @JsonProperty("color")
    private String color;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id", insertable=false, updatable=false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    @JsonIgnore
    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL)
    List<Cart> carts;
}
