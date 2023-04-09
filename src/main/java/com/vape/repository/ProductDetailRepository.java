package com.vape.repository;

import com.vape.entity.Product;
import com.vape.entity.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {
    List<ProductDetail> findAllByProduct(Product product);
}
