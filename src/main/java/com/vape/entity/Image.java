package com.vape.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Image implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("id")
    private Long id;

    @JsonProperty("link")
    private String link;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id", insertable=false, updatable=false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;
}
