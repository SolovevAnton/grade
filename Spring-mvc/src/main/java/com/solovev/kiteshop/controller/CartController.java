package com.solovev.kiteshop.controller;

import com.solovev.kiteshop.common.APINamings;
import com.solovev.kiteshop.common.TemplatesNamings;
import com.solovev.kiteshop.config.SessionAttributesControllerAdvice;
import com.solovev.kiteshop.model.order.Cart;
import com.solovev.kiteshop.model.product.Product;
import com.solovev.kiteshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@RestController
@RequestMapping(APINamings.CART)
@SessionAttributes(SessionAttributesControllerAdvice.CART_ATTR)
@RequiredArgsConstructor
public class CartController {
    private final ProductService productService;

    @GetMapping
    public ModelAndView showCart(@ModelAttribute(SessionAttributesControllerAdvice.CART_ATTR) Cart currentCart) {
        ModelAndView mv = new ModelAndView(TemplatesNamings.CART);
        Map<Product, Integer> products = productService.get(currentCart);
        mv.addObject("cart", currentCart);
        mv.addObject("products", products);
        return mv;
    }

    @PostMapping("/{productId}")
    public RedirectView addProductToCart(@ModelAttribute(SessionAttributesControllerAdvice.CART_ATTR) Cart currentCart,
                                         @PathVariable long productId,
                                         @RequestParam int quantity) {
        currentCart.addItem(productId, quantity);
        return new RedirectView(APINamings.CATALOG);
    }

}
