package com.vape.controller;

import com.google.gson.Gson;
import com.vape.entity.Category;
import com.vape.entity.Product;
import com.vape.model.base.VapeResponse;
import com.vape.model.base.Error;
import com.vape.model.reponse.ProductResponse;
import com.vape.model.request.CustomPageRequest;
import com.vape.model.request.CustomProductRequest;
import com.vape.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin()
public class ProductController {

    @Autowired
    ProductService productService;

    @PostMapping("product/create")
    public VapeResponse<Object> createProduct(
            @RequestParam("files") MultipartFile[] files,
            @RequestParam("data") String data,
            @RequestParam("avatar") MultipartFile avatar
    ) {
        CustomProductRequest request = new Gson().fromJson(data, CustomProductRequest.class);
        if (request.isValid()) {
            Product product = productService.createProduct(request, files, avatar);
            return product == null
                    ? VapeResponse.newInstance(Error.NOT_OK, null)
                    : VapeResponse.newInstance(Error.OK, product);
        } else {
            return VapeResponse.newInstance(Error.INVALID_PARAM, null);
        }
    }

    @PostMapping("/products/getAll/{categoryId}")
    public VapeResponse<Page<ProductResponse>> getAllProducts(@RequestBody CustomPageRequest request, @PathVariable Long categoryId) {
        request.checkData();
        Page<ProductResponse> products;
        if (request.getKeySearch() != null) {
            products = productService.getAllProductByName(
                    request.getPageNumber(),
                    request.getPageSize(),
                    request.getSortField(),
                    request.getSortOrder(),
                    request.getKeySearch(),
                    categoryId
            );
        } else {
            products = productService.getAllProduct(
                    request.getPageNumber(),
                    request.getPageSize(),
                    request.getSortField(),
                    request.getSortOrder(),
                    categoryId
            );
        }
        return (products != null && !products.isEmpty())
                ? VapeResponse.newInstance(Error.OK.getErrorCode(), Error.OK.getMessage(), products)
                : VapeResponse.newInstance(Error.EMPTY.getErrorCode(), Error.EMPTY.getMessage(), products);
    }

    @PostMapping("/products/{categoryId}")
    public VapeResponse<Page<ProductResponse>> getAllProducts1(@RequestBody CustomPageRequest request, @PathVariable Long categoryId) {
        request.checkData();
        Page<ProductResponse> products;
        if (request.getKeySearch() != null) {
            products = productService.getAllProductByName(
                    request.getPageNumber(),
                    request.getPageSize(),
                    request.getSortField(),
                    request.getSortOrder(),
                    request.getKeySearch(),
                    categoryId
            );
        } else {
            products = productService.getAllProduct(
                    request.getPageNumber(),
                    request.getPageSize(),
                    request.getSortField(),
                    request.getSortOrder(),
                    categoryId
            );
        }
        return (products != null && !products.isEmpty())
                ? VapeResponse.newInstance(Error.OK.getErrorCode(), Error.OK.getMessage(), products)
                : VapeResponse.newInstance(Error.EMPTY.getErrorCode(), Error.EMPTY.getMessage(), products);
    }

    @GetMapping("/product/{productId}")
    public VapeResponse<Object> getProductById(@PathVariable Long productId) {
        if (productId == null) {
            return VapeResponse.newInstance(Error.INVALID_PARAM, null);
        }
        ProductResponse productResponse = productService.getProductById(productId);
        return productResponse == null
                ? VapeResponse.newInstance(Error.INVALID_PARAM, null)
                : VapeResponse.newInstance(Error.OK, productResponse);
    }

    @PutMapping("/product/{productId}")
    public VapeResponse<Object> updateProduct(
            @PathVariable("productId") Long productId,
            @RequestParam(value = "files", required = false) MultipartFile[] files,
            @RequestParam("data") String data,
            @RequestParam(value = "avatar", required = false) MultipartFile avatar
    ) {
        if (productId == null) return VapeResponse.newInstance(Error.INVALID_PARAM, null);
        CustomProductRequest request;
        try {
            request = new Gson().fromJson(data, CustomProductRequest.class);
            if (!request.isValid()) {
                throw new RuntimeException("Dữ liệu truyền lên không được để trống");
            }
        } catch (Exception e) {
            return VapeResponse.newInstance(Error.INVALID_PARAM, null);
        }
        Product product = productService.updateProduct(productId, request, files, avatar);
        return product != null
                ? VapeResponse.newInstance(Error.OK, product)
                : VapeResponse.newInstance(Error.NOT_OK, null);
    }

    @DeleteMapping("/product/{productId}")
    public VapeResponse<Object> deleteProduct(@PathVariable("productId") Long productId) {
        boolean isSuccess = productService.deleteProduct(productId);
        return isSuccess
                ? VapeResponse.newInstance(Error.OK, "Xóa thành công product")
                : VapeResponse.newInstance(Error.NOT_OK, "Có lỗi xảy ra khi xóa product có ID = " + productId);
    }

    @GetMapping("/products")
    public VapeResponse<Object> getAllProduct() {
        List<Product> products = productService.getAllProduct();
        return (products != null && !products.isEmpty())
                ? VapeResponse.newInstance(Error.OK, products)
                : VapeResponse.newInstance(Error.NOT_OK, products);
    }
}
