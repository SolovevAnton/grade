package com.solovev.kiteshop.model;

import com.solovev.kiteshop.BaseDBRelatedConfig;
import com.solovev.kiteshop.model.order.Order;
import com.solovev.kiteshop.model.user.User;
import com.solovev.kiteshop.repository.OrderRepo;
import com.solovev.kiteshop.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_CLASS;

@Sql(scripts = {"/users.sql", "/orders.sql"}, executionPhase = BEFORE_TEST_CLASS)
class UserRelationshipTests extends BaseDBRelatedConfig {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private OrderRepo orderRepo;

    @Test
    public void emptyTestShouldSuccessfullyStartContext() {
    }

    @Test
    public void shouldSuccessfullyAddOrder() {
        //given
        var usersWithOrders = orderRepo.findAll().stream().map(Order::getUser).collect(Collectors.toSet());
        User userWithoutOrders =
                userRepo.findAll().stream().filter(u -> !usersWithOrders.contains(u)).findAny().orElseThrow();
        Order orderToAdd = new Order(userWithoutOrders);
        assertFalse(orderRepo.exists(Example.of(orderToAdd)));
        //when
        orderRepo.save(orderToAdd);
        //then
        assertEquals(orderToAdd, orderRepo.findById(orderToAdd.getId()).get());
    }

    @Test
    public void shouldNotSuccessfullyAddOrderForNonExistentUser() {
        //given
        User nonExistentUser = new User("Nonexistent", "NonExistentPass");
        Order orderToAdd = new Order(nonExistentUser);
        assumeFalse(userRepo.exists(Example.of(nonExistentUser)));
        assumeFalse(orderRepo.exists(Example.of(orderToAdd)));
        //then
        assertThrows(DataAccessException.class, () -> orderRepo.save(orderToAdd));
        assertNull(orderToAdd.getId());
    }

    @Test
    public void shouldNotDeleteUserWhenDeletingOrder() {
        //given
        Order orderToDelete = orderRepo.findById(1L).orElseThrow();
        List<Order> expectedRepoWithoutDeletedOrder = orderRepo.findAll().stream().filter(o -> !Objects.equals(o,
                orderToDelete)).toList();
        List<User> initialUsers = userRepo.findAll();
        //when
        orderRepo.delete(orderToDelete);
        //then
        assertEquals(expectedRepoWithoutDeletedOrder, orderRepo.findAll());
        assertEquals(initialUsers, userRepo.findAll());
    }

    @Test
    public void deleteUserShouldDeleteAllAssociatedOrders() {
        //given
        User userToDelete = userRepo.findById(1L).orElseThrow();
        List<Order> expectedRepoWithoutOrdersOfDeletedUser =
                orderRepo.findAll().stream().filter(o -> !Objects.equals(o.getUser(), userToDelete)).toList();
        assumeFalse(expectedRepoWithoutOrdersOfDeletedUser.isEmpty());
        //when
        userRepo.delete(userToDelete);
        //then
        assertEquals(expectedRepoWithoutOrdersOfDeletedUser, orderRepo.findAll());
    }

}