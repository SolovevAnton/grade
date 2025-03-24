package com.solovev.kiteshop.controller;

import com.solovev.kiteshop.common.APINamings;
import com.solovev.kiteshop.common.TemplatesNamings;
import com.solovev.kiteshop.config.SessionAttributesControllerAdvice;
import com.solovev.kiteshop.model.order.Cart;
import com.solovev.kiteshop.model.order.Order;
import com.solovev.kiteshop.model.user.User;
import com.solovev.kiteshop.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping(APINamings.ORDER)
@SessionAttributes(SessionAttributesControllerAdvice.CART_ATTR)
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ModelAndView getOrders(@AuthenticationPrincipal User user) {
        ModelAndView mv = new ModelAndView(TemplatesNamings.ORDERS_ONE_USER);
        mv.addObject("user", user);
        mv.addObject("orders", orderService.getOrdersForUsername(user.getUsername()));
        return mv;
    }

    @GetMapping("/{orderId}")
    public ModelAndView getOrder(@PathVariable long orderId) {
        Order foundOrder = orderService.getOrder(orderId);

        ModelAndView mv = new ModelAndView(TemplatesNamings.ORDER);
        mv.addObject("user", foundOrder.getUser());
        mv.addObject("order", foundOrder);
        return mv;
    }

    @PostMapping
    public RedirectView createOrder(
            @SessionAttribute(SessionAttributesControllerAdvice.CART_ATTR) @Valid Cart cart,
            @AuthenticationPrincipal UserDetails userDetails,
            SessionStatus sessionStatus
    ) {
        long orderId = orderService.createAndAddOrder(userDetails, cart).getId();
        sessionStatus.setComplete();
        return new RedirectView(APINamings.ORDER + "/" + orderId);
    }

}
