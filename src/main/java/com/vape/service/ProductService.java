package com.vape.service;

import com.vape.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    Page<Product> getAllProduct();
}
