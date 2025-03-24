package com.solovev.kiteshop.controller;

import com.solovev.kiteshop.common.APINamings;
import com.solovev.kiteshop.common.TemplatesNamings;
import com.solovev.kiteshop.config.SessionAttributesControllerAdvice;
import com.solovev.kiteshop.model.order.Cart;
import com.solovev.kiteshop.model.product.Product;
import com.solovev.kiteshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RestController
@RequestMapping(APINamings.CATALOG)
@SessionAttributes(SessionAttributesControllerAdvice.CART_ATTR)
@RequiredArgsConstructor
public class CatalogController {
    private final ProductService productService;

    /**
     * Method to get all kites in the shop
     *
     * @param sort        - parameter contracted from request by Spring
     * @param currentCart cart of the user
     * @return Model or bad request in case sort parameter having some attr not presented in kite models
     */
    @GetMapping
    public ModelAndView getCatalog(@SortDefault(sort = {"model"}, direction = Sort.Direction.ASC) Sort sort,
                                   @ModelAttribute(SessionAttributesControllerAdvice.CART_ATTR) Cart currentCart) {
        try {
            List<Product> products = productService.get(sort);
            ModelAndView mv = new ModelAndView(TemplatesNamings.CATALOG);
            mv.addObject("products", products);
            mv.addObject("cart", currentCart);
            return mv;
        } catch (PropertyReferenceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
