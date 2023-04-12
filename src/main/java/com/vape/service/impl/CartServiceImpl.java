package com.vape.service.impl;

import com.vape.entity.Cart;
import com.vape.model.request.CartRequest;
import com.vape.repository.CartRepository;
import com.vape.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Override
    public List<Cart> getAll(String email) {
        return cartRepository.findAllCart(email);
    }

    @Override
    public Cart createCart(CartRequest cartRequest) {
        return cartRepository.save(Cart.builder()
                .email(cartRequest.getEmail())
                .status(cartRequest.getStatus())
                .quantity(cartRequest.getQuantity()).build());
    }
}
