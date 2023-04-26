package com.vape.repository;

import com.vape.entity.Category;
import com.vape.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByNameContainingIgnoreCaseAndCategoriesIn(String name, List<Category> categories, Pageable pageable);
    Page<Product> findAllByCategoriesIn(List<Category> categories, Pageable pageable);
    Page<Product> findAll(Specification<Product> spec, Pageable pageable);
    List<Product> findAll();

}
