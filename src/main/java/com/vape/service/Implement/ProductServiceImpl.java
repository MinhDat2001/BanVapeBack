package com.vape.service.Implement;

import com.vape.entity.Product;
import com.vape.model.comunication.PageSortRequest;
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
    public List<Product> getAllProduct(PageSortRequest request) {
        request.checkData();
        Pageable pageProduct = (Pageable) PageRequest.of(request.getPageNumber(), request.getPageSize());
        Sort sort = Sort.by(request.getSortField()).descending();
        return productRepository.findAll(pageProduct, sort).toList();
    }
}
