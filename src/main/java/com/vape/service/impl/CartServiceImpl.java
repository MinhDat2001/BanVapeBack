package com.vape.service.impl;

import com.vape.entity.Cart;
import com.vape.model.CartStatus;
import com.vape.model.request.CartRequest;
import com.vape.repository.CartRepository;
import com.vape.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Override
    public List<Cart> getAll(String email) {
        return cartRepository.findAllCart(email);
    }

    @Override
    public Optional<Cart> getOne(Long id) {
        return cartRepository.findById(id);
    }

    @Override
    public Cart createCart(CartRequest cartRequest) {
        Optional<Cart> cart = cartRepository.findById(cartRequest.getId());
        if (cart.isPresent()){
            return null;
        }
        return cartRepository.save(Cart.builder()
                .email(cartRequest.getEmail())
                .status(cartRequest.getStatus())
                .quantity(cartRequest.getQuantity()).build());
    }

    @Override
    public Cart updateCart(CartRequest cartRequest) {
        Cart cart = cartRepository.getById(cartRequest.getId());
        cart.setQuantity(cartRequest.getQuantity());
        cart.setStatus(cartRequest.getStatus());
        return cartRepository.save(cart);
    }

    @Override
    public boolean deleteCart(Long id) {
        Optional<Cart> cart = cartRepository.findById(id);
        if (!cart.isPresent()){
            return false;
        }
        cartRepository.deleteById(id);
        return true;
    }
}
