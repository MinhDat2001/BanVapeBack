package com.vape.controller;

import com.vape.Constant.Constant;
import com.vape.config.JwtTokenUtil;
import com.vape.entity.Cart;
import com.vape.model.base.Error;
import com.vape.model.base.VapeResponse;
import com.vape.model.request.CartRequest;
import com.vape.service.CartService;
import com.vape.view.CartView;
import com.vape.view.DetailView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

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
        List<CartView> carts = cartService.getAll(username).stream().map(cart -> CartView.builder()
                .id(cart.getId())
                .email(cart.getEmail())
                .status(cart.getStatus())
                .quantity(cart.getQuantity())
                .detailView(DetailView.builder()
                        .productID(cart.getProductDetail().getProduct().getId())
                        .id(cart.getProductDetail().getId())
                        .color(cart.getProductDetail().getColor())
                        .avatar(cart.getProductDetail().getAvatar())
                        .quantity(cart.getProductDetail().getQuantity())
                        .build())
                .build()).collect(Collectors.toList());
        System.out.println(VapeResponse.newInstance(Error.OK, carts));
        return VapeResponse.newInstance(Error.OK, carts);
    }

    @PostMapping("/create")
    public VapeResponse<Object> createCart(@RequestBody CartRequest cartRequest){
        return VapeResponse.newInstance(Error.OK, cartService.createCart(cartRequest));
    }
}
