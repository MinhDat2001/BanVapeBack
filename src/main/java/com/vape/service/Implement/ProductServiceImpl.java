package com.vape.service.Implement;

import com.vape.entity.Product;
import com.vape.repository.ProductRepository;
import com.vape.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

public class ProductServiceImpl  implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Page<Product> getAllProduct() {
        Pageable pageProduct = (Pageable) PageRequest.of(0,9);
        Sort sort = Sort.by("name", "price").descending();
//        Page<Product> allProduct = productRepository.findAll(Sort.by("name", "price", "quantity"));
        return productRepository.findAll(pageProduct, sort);
    }
}
