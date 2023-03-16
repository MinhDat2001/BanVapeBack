package com.vape.service;

import com.vape.entity.Product;
import com.vape.model.comunication.PageSortRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    List<Product> getAllProduct(PageSortRequest request);
}
