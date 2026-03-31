package ru.Daniil.NauJava.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.Daniil.NauJava.AOP.Logger;
import ru.Daniil.NauJava.model.Cart;
import ru.Daniil.NauJava.repo.CartRepo;
import ru.Daniil.NauJava.service.CartService;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepo cartRepo;

    @Override
    @Logger
    public List<Cart> getAllCarts() {
        return cartRepo.findAll();
    }

    @Override
    @Logger
    public Cart getCartById(Long id) {
        return cartRepo.findById(id).get();
    }

    @Override
    public Cart createCart(Cart item) {
        return null;
    }
}