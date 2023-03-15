package com.vape.service;

import com.vape.entity.Category;
import com.vape.entity.Product;
import com.vape.entity.ProductDetail;
import com.vape.model.reponse.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProductService {
    Product getProductById(Long id);

    Page<ProductResponse> getAllProduct(int pageNumber, int pageSize, String sortField, String sortOrder);

    ProductDetail getProductDetailById();

    List<Category> getAllCategory();

}
