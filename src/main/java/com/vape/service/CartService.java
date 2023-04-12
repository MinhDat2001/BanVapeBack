package com.vape.service;

import com.vape.entity.Cart;
import com.vape.model.CartStatus;
import com.vape.model.request.CartRequest;

import java.util.List;
import java.util.Optional;

public interface CartService {
    List<Cart> getAll(String email);
    Optional<Cart> getOne(Long id);
    Cart createCart(CartRequest cartRequest);
    Cart updateCart(CartRequest cartRequest);
    boolean deleteCart(Long id);
}
