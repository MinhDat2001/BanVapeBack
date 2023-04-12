package com.vape.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.vape.model.CartStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "cart")
public class Cart implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private int quantity;
    private CartStatus status;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id", insertable=false, updatable=false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id", insertable=false, updatable=false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private ProductDetail productDetail;
}
