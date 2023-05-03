package com.vape.service;

import com.vape.entity.Category;
import com.vape.entity.Product;
import com.vape.entity.ProductDetail;
import com.vape.model.reponse.ProductResponse;
import com.vape.model.request.CustomProductRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface ProductService {
    ProductResponse getProductById(Long id);

    Page<ProductResponse> getAllProduct(int pageNumber, int pageSize, String sortField, String sortOrder, Long categoryId);

    Page<ProductResponse> getAllProductByName(int pageNumber, int pageSize, String sortField, String sortOrder, String key, Long categoryId);

    List<Product> getAllProduct();

    Product createProduct(CustomProductRequest request, MultipartFile[] files, MultipartFile avatar);

    Product updateProduct(Long productId, CustomProductRequest request, MultipartFile[] files, MultipartFile avatar);

    Boolean deleteProduct(Long productId);

    Page<Product> getAllProductPaging(int pageNumber, int pageSize, String sortField, String sortOrder);

    Page<Product> getAllProductByNamePaging(int pageNumber, int pageSize, String sortField, String sortOrder, String key);

    boolean deleteImageById(Long id);
}
