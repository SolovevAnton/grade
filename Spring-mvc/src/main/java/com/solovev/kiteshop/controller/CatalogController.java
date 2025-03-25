package com.solovev.kiteshop.controller;

import com.solovev.kiteshop.common.APINamings;
import com.solovev.kiteshop.common.TemplatesNamings;
import com.solovev.kiteshop.config.SessionAttributesControllerAdvice;
import com.solovev.kiteshop.model.order.Cart;
import com.solovev.kiteshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import static com.solovev.kiteshop.common.PageableDefaultConfig.DEFAULT_PAGE_SIZE;
import static com.solovev.kiteshop.common.PageableDefaultConfig.SORT_COLUMN;

@RestController
@RequestMapping(APINamings.CATALOG)
@SessionAttributes(SessionAttributesControllerAdvice.CART_ATTR)
@RequiredArgsConstructor
@Slf4j
public class CatalogController {
    private final ProductService productService;

    /**
     * Method to get all kites in the shop
     *
     * @param pageable    - parameter contracted from request by Spring
     * @param currentCart cart of the user
     * @return Model or bad request in case sort parameter having some attr not presented in kite models
     */
    @GetMapping
    public ModelAndView getCatalog(
            @PageableDefault(
                    size = DEFAULT_PAGE_SIZE,
                    sort = {SORT_COLUMN},
                    direction = Sort.Direction.DESC) Pageable pageable,
            @ModelAttribute(SessionAttributesControllerAdvice.CART_ATTR) Cart currentCart) {
        try {
            var productPage = productService.get(pageable);
            ModelAndView mv = new ModelAndView(TemplatesNamings.CATALOG);
            mv.addObject("products", productPage);
            mv.addObject("cart", currentCart);
            mv.addObject("currentPage", pageable.getPageNumber());
            mv.addObject("totalPages", productPage.getTotalPages());
            mv.addObject("selectedSort", selectedSort(pageable.getSort()));
            return mv;
        } catch (PropertyReferenceException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    private String selectedSort(Sort sort) {
        Sort.Order order = sort.iterator().next();
        String result = order.getProperty() + "," + order.getDirection();
        log.info("selectedSort: [{}]", result);
        return result;
    }
}
