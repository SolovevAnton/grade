package com.solovev.kiteshop.service;

import com.solovev.kiteshop.model.order.Cart;
import com.solovev.kiteshop.model.order.Order;
import com.solovev.kiteshop.repository.OrderRepo;
import jakarta.persistence.NonUniqueResultException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {
    private final OrderRepo orderRepo;
    private final OrderPositionService orderPositionService;
    private final UserService userService;

    @Transactional(readOnly = true)
    public Collection<Order> getAll() {
        return orderRepo.findAll();
    }

    @PostAuthorize("hasAuthority('ADMIN') or returnObject.user.username == authentication.principal.username")
    @Transactional(readOnly = true)
    public Order getOrder(long id) throws NoSuchElementException {
        return orderRepo.findById(id).orElseThrow();
    }

    @Transactional(readOnly = true)
    public Collection<Order> getOrdersForUsername(String userName) {
        return orderRepo.getOrderByUser_Username(userName);
    }

    @Transactional
    public Order createAndAddOrder(UserDetails userDetails, Cart cart) {
        Order toAdd = getRegisteredOrCreateNewOrderForUserName(userDetails.getUsername());
        orderRepo.save(toAdd); // to update modified column
        orderPositionService.createAndAddPositionsFromCart(toAdd, cart);
        return toAdd;
    }

    private Order getRegisteredOrCreateNewOrderForUserName(String userName) {
        var orders = orderRepo.getOrderByUser_UsernameAndStatus(userName, Order.Status.REGISTERED);
        if (orders.size() > 1) {
            log.error("User {} has more than one order in registered status. Orders in registered for this user: {}",
                    userName, orders);
            throw new NonUniqueResultException("There are more than one order in registered status for user with " +
                    "username: " + userName);
        }
        return orders.isEmpty() ? new Order(userService.loadUserByUsername(userName)) : orders.get(0);
    }
}
