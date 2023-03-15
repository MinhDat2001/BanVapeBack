package com.vape.service.impl;

import com.vape.entity.Category;
import com.vape.entity.Product;
import com.vape.entity.ProductDetail;
import com.vape.model.reponse.ProductResponse;
import com.vape.repository.ProductRepository;
import com.vape.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product getProductById(Long id) {
        return null;
    }

    @Override
    public Page<ProductResponse> getAllProduct(int pageNumber, int pageSize, String sortField, String sortOrder) {
        Sort sort = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        return productRepository.findAll(pageable).map(product ->
                ProductResponse.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .avatar(product.getAvatar())
                        .quantity(product.getQuantity())
                        .price(product.getPrice())
                        .description(product.getDescription())
                        .build());
    }

    @Override
    public ProductDetail getProductDetailById() {
        return null;
    }

    @Override
    public List<Category> getAllCategory() {
        return null;
    }
}
