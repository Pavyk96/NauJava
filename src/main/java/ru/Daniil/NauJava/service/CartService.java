package ru.Daniil.NauJava.service;

import ru.Daniil.NauJava.model.Cart;

import java.util.List;

/**
 * CartService
 *
 * @author Daniil Mezev
 */
public interface CartService {
    Cart getCartById(Long id);
    Cart createCart(Cart item);
    List<Cart> getAllCarts();
}
