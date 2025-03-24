package com.solovev.kiteshop.service;

import com.solovev.kiteshop.BaseDBRelatedConfig;
import com.solovev.kiteshop.model.order.Cart;
import com.solovev.kiteshop.model.order.Order;
import com.solovev.kiteshop.model.user.User;
import com.solovev.kiteshop.repository.OrderPositionRepo;
import com.solovev.kiteshop.repository.OrderRepo;
import com.solovev.kiteshop.repository.ProductRepo;
import com.solovev.kiteshop.repository.UserRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.jdbc.Sql;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_CLASS;

@Sql(scripts = {
        "/users.sql",
        "/products.sql",
        "/orders.sql",
        "/order_positions.sql"
}, executionPhase = BEFORE_TEST_CLASS)
class OrderServiceIntegrationTest extends BaseDBRelatedConfig {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderPositionService orderPositionService;
    @Autowired
    private UserService userService;

    @Test
    public void shouldAddEmptyCartToOrder() {
        //given
        Cart cart = new Cart();

        var usersWithRegisteredOrders =
                orderService.getAll().stream().filter(Order::isRegistered).map(Order::getUser).toList();
        User existingUserWithoutOrders =
                userService.findAll().stream().filter(u -> !usersWithRegisteredOrders.contains(u)).findAny()
                        .orElseThrow();
        UserDetails mockedDetails = mock(UserDetails.class);
        when(mockedDetails.getUsername()).thenReturn(existingUserWithoutOrders.getUsername());

        //when
        var created = orderService.createAndAddOrder(mockedDetails, cart);

        //then
        assertTrue(orderService.getOrdersForUsername(existingUserWithoutOrders.getUsername()).contains(created));
        assertTrue(orderPositionService
                .getAll()
                .stream()
                .filter(op -> op.getOrder().equals(created))
                .toList().isEmpty());
    }

    @Test
    public void shouldThrowIfUsernameWasNotFoundOrder() {
        //given
        Cart cart = new Cart();
        cart.addItem(1L, 2);
        cart.addItem(2L, 1);

        UserDetails mockedDetails = mock(UserDetails.class);
        when(mockedDetails.getUsername()).thenReturn("non existing username");

        //when
        assertThrows(UsernameNotFoundException.class, () -> orderService.createAndAddOrder(mockedDetails, cart));
    }

    @Test
    public void addingCartToUserWithoutExistingOrderShouldCreateAndAddNewOrder() {
        //given
        Cart cart = new Cart();
        cart.addItem(1L, 2);
        cart.addItem(2L, 1);

        var usersWithRegisteredOrders =
                orderService.getAll().stream().filter(Order::isRegistered).map(Order::getUser).toList();
        User existingUserWithoutOrders =
                userService.findAll().stream().filter(u -> !usersWithRegisteredOrders.contains(u)).findAny()
                        .orElseThrow();
        UserDetails mockedDetails = mock(UserDetails.class);
        when(mockedDetails.getUsername()).thenReturn(existingUserWithoutOrders.getUsername());

        //when
        Order created = orderService.createAndAddOrder(mockedDetails, cart);

        //then
        assertTrue(orderService.getOrdersForUsername(existingUserWithoutOrders.getUsername()).contains(created));
        assertTrue(orderPositionService
                .getAll()
                .stream()
                .filter(op -> op.getOrder().equals(created))
                .allMatch(op -> cart.getQuantity(op.getProduct().getId()) == op.getQuantity()));
    }

    @Test
    public void addingCartToUserWithExistingOrderShouldAddPositionsToOrder() {
        //given
        Map<Long, Integer> expectedIdsAndQuantity = new HashMap<>();

        Cart cart = new Cart();
        cart.addItem(3L, 1); //product not in order
        expectedIdsAndQuantity.put(3L, 1);

        User userWithRegisteredOrders =
                orderService.getAll().stream()
                        .filter(Order::isRegistered)
                        .filter(o -> !orderPositionService.getAllByOrder(o).isEmpty())
                        .map(Order::getUser)
                        .findAny().orElseThrow();

        Order registeredOrder =
                orderService.getAll().stream().filter(Order::isRegistered)
                        .filter(o -> o.getUser().equals(userWithRegisteredOrders)).findFirst().orElseThrow();
        //add existing in order products to cart.
        orderPositionService.getAllByOrder(registeredOrder).forEach(op -> {
            expectedIdsAndQuantity.put(op.getProduct().getId(), op.getQuantity() + 1);
            cart.addItem(op.getProduct().getId(), 1);
        });

        UserDetails mockedDetails = mock(UserDetails.class);
        when(mockedDetails.getUsername()).thenReturn(userWithRegisteredOrders.getUsername());

        //when
        Order created = orderService.createAndAddOrder(mockedDetails, cart);

        //then
        assertTrue(orderService.getOrdersForUsername(userWithRegisteredOrders.getUsername()).contains(created));
        assertTrue(orderPositionService.getAll()
                .stream()
                .filter(op -> op.getOrder().equals(created))
                .allMatch(op -> expectedIdsAndQuantity.get(op.getProduct().getId()) == op.getQuantity()));
        assertEquals(expectedIdsAndQuantity.size(),
                orderPositionService.getAll()
                        .stream()
                        .filter(op -> op.getOrder().equals(created)).count());
    }

    @TestConfiguration
    public static class Configure {
        //repos
        @Autowired
        private UserRepo userRepo;
        @Autowired
        private OrderRepo orderRepo;
        @Autowired
        private OrderPositionRepo orderPositionRepo;
        @Autowired
        private ProductRepo productRepo;

        @Bean
        public OrderService orderService() {
            return new OrderService(orderRepo, orderPositionService(), userService());
        }

        @Bean
        public UserService userService() {
            return new UserService(userRepo);
        }

        @Bean
        public OrderPositionService orderPositionService() {
            return new OrderPositionService(orderPositionRepo, productService());
        }

        @Bean
        public ProductService productService() {
            return new ProductService(productRepo);
        }
    }
}