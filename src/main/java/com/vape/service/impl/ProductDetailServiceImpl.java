package com.vape.service.impl;

import com.vape.cloudinary.CloudinaryService;
import com.vape.entity.Product;
import com.vape.entity.ProductDetail;
import com.vape.model.request.ProductDetailRequest;
import com.vape.repository.ProductDetailRepository;
import com.vape.repository.ProductRepository;
import com.vape.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ProductDetailServiceImpl implements ProductDetailService {

    @Autowired
    ProductDetailRepository productDetailRepository;

    @Autowired
    CloudinaryService cloudinaryService;

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<ProductDetail> getProductDetailByProductId(Long productId) {
        return productDetailRepository.findAllByProduct_Id(productId);
    }

    @Override
    public boolean removeProductDetail(Long productDetailId) {
        try {
            productDetailRepository.findById(productDetailId)
                    .orElseThrow(() -> new RuntimeException("Không có product detail nào có ID = " + productDetailId));
            productDetailRepository.deleteById(productDetailId);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public ProductDetail createProductDetail(Long productId, ProductDetailRequest request, MultipartFile file) {
        Product product = productRepository.findById(productId).orElseThrow(
            () -> new RuntimeException("Không tìm thấy product có ID = " + productId + " để thêm product detail")
        );
        ProductDetail productDetail = ProductDetail.builder()
                .product(product)
                .color(request.getColor())
                .quantity(request.getQuantity())
                .build();

        if (file == null || file.isEmpty()) return null;
        String url = cloudinaryService.uploadURl(file);
        productDetail.setAvatar(url);
        return productDetailRepository.save(productDetail);
    }

    @Override
    public ProductDetail updateProductDetail(Long productDetailId, ProductDetailRequest request, MultipartFile file) {
        ProductDetail detail = productDetailRepository
                .findById(productDetailId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy product detail có ID = " + productDetailId));
        detail.setQuantity(request.getQuantity());
        detail.setColor(request.getColor());

        if (file != null && !file.isEmpty()) {
            String res = cloudinaryService.deleteFromUrl(detail.getAvatar());
            if (!res.equals("ok")) {
                System.out.println("URL Avatar cũ = " + detail.getAvatar() + " chưa được xóa trên cloudinary");
            } else {
                System.out.println("Đã xóa avatar cũ URL = " + detail.getAvatar());
            }
            String url = cloudinaryService.uploadURl(file);
            detail.setAvatar(url);
        }

        return productDetailRepository.save(detail);
    }

    @Override
    public ProductDetail getProductDetailById(Long productDetailId) {
        return productDetailRepository.findById(productDetailId).orElseThrow(
                () -> new RuntimeException("Không tìm thấy product detail có ID = " + productDetailId)
        );
    }
}
