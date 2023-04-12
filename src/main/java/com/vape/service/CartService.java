package com.vape.service;

import com.vape.entity.Cart;
import com.vape.model.request.CartRequest;

import java.util.List;

public interface CartService {
    List<Cart> getAll(String email);
    Cart createCart(CartRequest cartRequest);
}
