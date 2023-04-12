package com.vape.controller;

import com.vape.Constant.Constant;
import com.vape.config.JwtTokenUtil;
import com.vape.entity.Cart;
import com.vape.model.base.Error;
import com.vape.model.base.VapeResponse;
import com.vape.model.request.CartRequest;
import com.vape.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CartService cartService;

    @GetMapping("/getAll")
    public VapeResponse<Object> getAllCart(HttpServletRequest request){
        String requestTokenHeader = request.getHeader("token");
        String jwtToken = requestTokenHeader.substring(5);
        String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
        List<Cart> carts = cartService.getAll(username);
        System.out.println(VapeResponse.newInstance(Error.OK, carts));
        return VapeResponse.newInstance(Error.OK, carts);
    }

    @PostMapping("/create")
    public VapeResponse<Object> createCart(@RequestBody CartRequest cartRequest){
        return VapeResponse.newInstance(Error.OK, cartService.createCart(cartRequest));
    }
}
