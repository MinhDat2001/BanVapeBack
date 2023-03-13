package com.vape.service;

import com.vape.entity.Product;
import com.vape.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface ProductService {
    public Optional<Product> getAllProduct();
}
