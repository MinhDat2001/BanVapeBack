package com.vape.controller;

import com.google.gson.Gson;
import com.vape.entity.ProductDetail;
import com.vape.model.Product;
import com.vape.model.base.Error;
import com.vape.model.base.VapeResponse;
import com.vape.model.request.ProductDetailRequest;
import com.vape.service.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.management.loading.MLet;
import java.text.ParsePosition;
import java.util.List;

@RestController
@CrossOrigin()
public class ProductDetailController {

    @Autowired
    ProductDetailService productDetailService;

    @GetMapping("/product_details/{productId}")
    public VapeResponse<Object> getAllProductByProductId(@PathVariable("productId") Long productId) {
        List<ProductDetail> productDetails = productDetailService.getProductDetailByProductId(productId);
        if (productDetails == null || productDetails.isEmpty()) {
            return VapeResponse.newInstance(Error.EMPTY, null);
        }
        return VapeResponse.newInstance(Error.OK, productDetails);
    }

    @GetMapping("/product_detail/{productDetailId}")
    public VapeResponse<Object> getProductDetailById(@PathVariable("productDetailId") Long productDetailId) {
        ProductDetail productDetail = productDetailService.getProductDetailById(productDetailId);
        return productDetail != null
                ? VapeResponse.newInstance(Error.OK, productDetail)
                : VapeResponse.newInstance(Error.NOT_OK, null);
    }

    @PostMapping("/product_detail/{productId}")
    public VapeResponse<Object> createProductDetail(
            @PathVariable("productId") Long productId,
            @RequestParam(value = "file") MultipartFile file,
            @RequestParam(value = "detail") String detail
    ) {
        ProductDetailRequest request;
        try {
            request = new Gson().fromJson(detail, ProductDetailRequest.class);
        } catch (Exception e) {
            return VapeResponse.newInstance(Error.INVALID_PARAM, detail);
        }
        if (!request.isValid()) return VapeResponse.newInstance(Error.INVALID_PARAM, null);
        ProductDetail productDetail = productDetailService.createProductDetail(productId, request, file);
        return productDetail != null
                ? VapeResponse.newInstance(Error.OK, productDetail)
                : VapeResponse.newInstance(Error.NOT_OK, null);
    }

    @PutMapping("/product_detail/{productDetailId}")
    public VapeResponse<Object> updateProductDetail(
            @PathVariable("productDetailId") Long productDetailId,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "detail", required = false) String detail
            ) {
        ProductDetailRequest request;
        try {
            request = new Gson().fromJson(detail, ProductDetailRequest.class);
        } catch (Exception e) {
            return VapeResponse.newInstance(Error.INVALID_PARAM, detail);
        }
        ProductDetail productDetail = productDetailService.updateProductDetail(productDetailId, request, file);
        return productDetail != null
                ? VapeResponse.newInstance(Error.OK, productDetail)
                : VapeResponse.newInstance(Error.NOT_OK, null);
    }

    @DeleteMapping("/product_detail/{productDetailId}")
    public VapeResponse<Object> deleteProductDetail(@PathVariable("productDetailId") Long productDetailId) {
        boolean isSuccess = productDetailService.removeProductDetail(productDetailId);
        return isSuccess
                ? VapeResponse.newInstance(Error.OK, "Xóa thành công")
                : VapeResponse.newInstance(Error.NOT_OK, "Có lỗi xảy ra khi xóa");
    }
}
