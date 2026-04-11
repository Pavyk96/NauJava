package ru.Daniil.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.Daniil.NauJava.model.Cart;
import ru.Daniil.NauJava.service.CartService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * TestController
 *
 * @author Daniil Mezev
 */
@RestController
@RequestMapping("api/cart")
public class TestController {

    @Autowired
    private CartService cartService;

    @GetMapping("/all")
    public List<CartResponse> getCarts() {
        List<Cart> carts = cartService.getAllCarts();
        return carts.stream()
                .map(this::convertToCartResponse)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public CartResponse getCartById(@PathVariable Long id) {
        Cart cart = cartService.getCartById(id);
        return convertToCartResponse(cart);
    }

    private CartResponse convertToCartResponse(Cart cart) {
        List<ItemResponse> itemResponses = cart.getItems().stream()
                .map(item -> new ItemResponse(item.getName()))
                .collect(Collectors.toList());
        return new CartResponse(cart.getId(), cart.getName(), itemResponses);
    }
}