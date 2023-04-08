package com.vape.repository;

import com.vape.entity.Product;
import com.vape.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM ProductCategory pc WHERE pc.product = :product")
    void deleteProductCategory(@Param("product") Product product);

    List<ProductCategory> findAllByProduct(Product product);
}
