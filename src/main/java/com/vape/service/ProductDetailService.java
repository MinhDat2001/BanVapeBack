package com.vape.service;

import com.vape.entity.ProductDetail;
import com.vape.model.request.ProductDetailRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface ProductDetailService {
    List<ProductDetail> getProductDetailByProductId(Long productId);

    boolean removeProductDetail(Long productDetailId);

    ProductDetail createProductDetail(Long productId, ProductDetailRequest request, MultipartFile file);

    ProductDetail updateProductDetail(Long productDetailId, ProductDetailRequest productDetail, MultipartFile file);

    ProductDetail getProductDetailById(Long productDetailId);

    void updateProductDetail(ProductDetail productDetail);
}
