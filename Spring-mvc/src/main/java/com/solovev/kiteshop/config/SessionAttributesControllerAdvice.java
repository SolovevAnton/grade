package com.solovev.kiteshop.config;

import com.solovev.kiteshop.controller.CartController;
import com.solovev.kiteshop.model.order.Cart;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice(basePackageClasses = {CartController.class, CartController.class})
public class SessionAttributesControllerAdvice {
    public static final String CART_ATTR = "currentCart";

    @ModelAttribute(CART_ATTR)
    public Cart currentCart() {
        return new Cart();
    }
}
