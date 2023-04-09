package com.vape.entity;

import com.vape.entity.Category;
import com.vape.entity.Product;
import com.vape.entity.embeddable.ProductCategoryId;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "product_category")
public class ProductCategory {

    @EmbeddedId
    private ProductCategoryId id;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @MapsId("categoryId")
    @JoinColumn(name = "category_id")
    private Category category;
}