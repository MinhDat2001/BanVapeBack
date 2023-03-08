package com.vape.service;

import com.vape.entity.Product;
import com.vape.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;
    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }
}
