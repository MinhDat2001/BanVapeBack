package com.vape.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vape.ObjectToJson.ObjToJson;
import com.vape.entity.Product;
import com.vape.model.comunication.Response;
import com.vape.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(value = "/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ObjToJson<Product> objToJson;

    @GetMapping("/getAll")
    public Response getAllProduct(){
        return new Response(HttpStatus.OK.value(),
                "Lấy thông tin tất cả sản phẩm thành công",
                        objToJson.convert(productService.getAllProduct()));
    }

    @GetMapping("/getOne/{id}")
    public void getOneProduct(@PathVariable Long id){
        //repository
    }

    @PostMapping("/createProduct")
    public void createProduct(@RequestBody Product product){

    }

    @PostMapping("/updateProduct")
    public void updateProduct(Product product){

    }

    @DeleteMapping("/deleteProduct/{id}")
    public void deleteProduct(@PathVariable Long id){

    }
}
