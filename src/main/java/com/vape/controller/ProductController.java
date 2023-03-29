package com.vape.controller;

import com.vape.entity.Product;
import com.vape.model.base.VapePage;
import com.vape.model.base.VapeResponse;
import com.vape.model.base.Error;
import com.vape.model.reponse.ProductResponse;
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

    @PostMapping
    public VapeResponse<Boolean> createProduct() {
        return null;
    }

    @GetMapping("/products")
    public VapeResponse<Page<ProductResponse>> getAllProducts(@RequestBody CustomPageRequest request) {
        request.checkData();
        Page<ProductResponse> products = productService.getAllProduct(request.getPageNumber(),
                request.getPageSize(),
                request.getSortField(),
                request.getSortOrder());
        return (products != null && !products.isEmpty())
                ? VapeResponse.newInstance(Error.OK.getErrorCode(), Error.OK.getMessage(), products)
                : VapeResponse.newInstance(Error.EMPTY.getErrorCode(), Error.EMPTY.getMessage(), products);
    }
}
