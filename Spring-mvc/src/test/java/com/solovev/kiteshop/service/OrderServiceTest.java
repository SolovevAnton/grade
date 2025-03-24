package com.solovev.kiteshop.service;

import com.solovev.kiteshop.model.order.Cart;
import com.solovev.kiteshop.model.order.Order;
import com.solovev.kiteshop.model.user.User;
import com.solovev.kiteshop.repository.OrderRepo;
import jakarta.persistence.NonUniqueResultException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = OrderService.class)
@EnableMethodSecurity
class OrderServiceTest {
    @MockBean
    private OrderRepo orderRepo;
    @MockBean
    private OrderPositionService orderPositionService;
    @MockBean
    private UserService userService;

    @Autowired
    private OrderService orderService;


    @Test
    public void shouldThrowExceptionIfHaveMoreThanOneRegisteredOrder() {
        //given
        String searchedUsername = "username";
        UserDetails userDetails = mock(UserDetails.class);

        when(userDetails.getUsername()).thenReturn(searchedUsername);
        when(orderRepo.getOrderByUser_UsernameAndStatus(searchedUsername, Order.Status.REGISTERED))
                .thenReturn(List.of(mock(Order.class), mock(Order.class)));
        //then
        assertThrows(NonUniqueResultException.class, () -> orderService.createAndAddOrder(userDetails,
                mock(Cart.class)));
    }


    @Test
    @WithMockUser
    public void shouldNotAllowOrderGetForNotThisUser() {
        //given
        long orderNotOwnedByUserId = 1;
        Order order = new Order(mock(User.class));

        when(orderRepo.findById(orderNotOwnedByUserId)).thenReturn(Optional.of(order));
        //when
        assertThrows(AuthorizationDeniedException.class, () -> orderService.getOrder(orderNotOwnedByUserId));
    }

    @Test
    @WithMockUser(authorities = {"ADMIN", "USER"})
    public void shouldAllowOrderGetForNotThisUserButIsAdmin() {
        //given
        String userName = "userOwner";
        long orderNotOwnedByUserId = 1;
        User owner = new User(userName, "pass");
        Order order = new Order(owner);

        when(orderRepo.findById(orderNotOwnedByUserId)).thenReturn(Optional.of(order));
        //when
        assertEquals(order, orderService.getOrder(orderNotOwnedByUserId));
    }

    @Test
    @WithMockUser
    public void shouldAllowOrderGetForThisUser() {
        //given
        String userName = "user";
        long orderOwnedByUserId = 1;
        User owner = new User(userName, "pass");
        Order order = new Order(owner);

        when(orderRepo.findById(orderOwnedByUserId)).thenReturn(Optional.of(order));
        //when
        assertEquals(order, orderService.getOrder(orderOwnedByUserId));
    }


}