package com.vape.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter
@Getter
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL)
    private List<Image> images;

    @JsonManagedReference
    @OneToMany(cascade = CascadeType.ALL)
    private List<Vote> votes;
}
