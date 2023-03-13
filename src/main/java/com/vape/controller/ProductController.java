package com.vape.controller;

import com.vape.entity.Product;
import com.vape.model.base.VapePage;
import com.vape.model.base.VapeResponse;
import com.vape.model.base.Error;
import com.vape.model.request.CustomPageRequest;
import com.vape.repository.ProductRepository;
import com.vape.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin()
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @PostMapping
    public VapeResponse<Boolean> createProduct() {
        return null;
    }

    @PostMapping("/product")
    public VapeResponse<Page<Product>> getAllProducts(@RequestBody CustomPageRequest request) {
        request.checkData();
        Page<Product> products = productService.getAllProduct(request.getPageNumber(),
                request.getPageSize(),
                request.getSortField(),
                request.getSortOrder());
        return VapeResponse.newInstance(Error.OK.getErrorCode(), Error.OK.getMessage(), products);
    }

    @GetMapping("product")
    public VapeResponse<List<Product>> getAllProducts() {
        return VapeResponse.newInstance(Error.OK.getErrorCode(), Error.OK.getMessage(), productRepository.findAll());
    }
}
