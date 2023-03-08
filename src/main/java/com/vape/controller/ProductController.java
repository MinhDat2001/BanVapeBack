package com.vape.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.vape.ObjectToJson.ObjToJson;
import com.vape.entity.Product;
import com.vape.model.comunication.Response;
import com.vape.repository.ProductRepository;
import com.vape.service.ProductService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    private ObjToJson objToJson;

    @GetMapping("/getAll")
    public Response getAllProduct() throws JsonProcessingException {
        return new Response(HttpStatus.OK.value(),
                "Lấy thông tin tất cả sản phẩm thành công",
                objToJson.convert(productService.getAllProduct()));
    }

    @GetMapping("/getOne/{id}")
    public void getOneProduct(@PathVariable Long id){
        //repository
    }

    @PostMapping("/createProduct")
    public void createProduct(Product product){

    }

    @PostMapping("/updateProduct")
    public void updateProduct(Product product){

    }

    @DeleteMapping("/deleteProduct/{id}")
    public void deleteProduct(@PathVariable Long id){

    }
}
