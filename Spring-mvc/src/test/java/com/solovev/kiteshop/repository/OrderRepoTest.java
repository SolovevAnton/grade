package com.solovev.kiteshop.repository;

import com.solovev.kiteshop.BaseDBRelatedConfig;
import com.solovev.kiteshop.model.order.Order;
import com.solovev.kiteshop.model.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_CLASS;


@Sql(scripts = {"/users.sql",
        "/orders.sql"}, executionPhase = BEFORE_TEST_CLASS)
public class OrderRepoTest extends BaseDBRelatedConfig {
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private UserRepo userRepo;

    @Test
    public void shouldReturnAllOrdersForUsername() {
        //given
        User existingUser = orderRepo.findAll().stream().findAny().orElseThrow().getUser();
        var ordersForUser = orderRepo.findAll().stream().filter(o -> o.getUser().equals(existingUser)).toList();

        assertFalse(ordersForUser.isEmpty());
        //then
        assertEquals(ordersForUser, orderRepo.getOrderByUser_Username(existingUser.getUsername()));
    }

    @Test
    public void shouldReturnNoOrdersForUsernameWithoutOrders() {
        //given
        var existingUsers = orderRepo.findAll().stream().map(Order::getUser).toList();
        var usersWithoutOrders = userRepo.findAll();
        usersWithoutOrders.removeAll(existingUsers);
        User userWithoutOrders = usersWithoutOrders.stream().findAny().orElseThrow();
        //then
        assertTrue(orderRepo.getOrderByUser_Username(userWithoutOrders.getUsername()).isEmpty());
    }

    @Test
    public void shouldReturnNoOrdersForNotExistingUserName() {
        //given
        String notExistingUsername = "not existing username";
        assertTrue(userRepo.findUserByUsername(notExistingUsername).isEmpty());
        //then
        assertTrue(orderRepo.getOrderByUser_Username(notExistingUsername).isEmpty());
    }

    @Test
    public void shouldAddOrderWithCorrectCreatedDate() {
        //given
        User existingUser = userRepo.findAll().stream().findAny().orElseThrow();
        Order order = new Order();
        order.setUser(existingUser);

        assertNull(order.getCreated());
        assertNull(order.getUpdated());
        //when
        orderRepo.saveAndFlush(order);

        //then
        assertTrue(order.getCreated().isBefore(LocalDateTime.now()));
        assertTrue(order.getUpdated().isBefore(LocalDateTime.now()));
    }

    @Test
    public void shouldModifyOrderAndWriteCorrectUpdatedDate() {
        //given
        Order existingOrderorder = orderRepo.findAll().stream().findAny().orElseThrow();
        var currentCreatedDate = existingOrderorder.getCreated();
        var currentUpdatedDate = existingOrderorder.getUpdated();

        assertNotNull(currentCreatedDate);
        assertNotNull(currentUpdatedDate);
        existingOrderorder.setStatus(Order.Status.CANCELED);
        //when
        orderRepo.saveAndFlush(existingOrderorder);

        //then
        assertEquals(currentCreatedDate, existingOrderorder.getCreated());
        assertTrue(existingOrderorder.getUpdated().isBefore(LocalDateTime.now()));
        assertTrue(existingOrderorder.getUpdated().isAfter(currentUpdatedDate));
    }

}
