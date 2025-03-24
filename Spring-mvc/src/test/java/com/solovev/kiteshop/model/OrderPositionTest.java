package com.solovev.kiteshop.model;

import com.solovev.kiteshop.model.order.Order;
import com.solovev.kiteshop.model.order.OrderPosition;
import com.solovev.kiteshop.model.product.Product;
import com.solovev.kiteshop.repository.OrderPositionRepo;
import com.solovev.kiteshop.repository.OrderRepo;
import com.solovev.kiteshop.repository.ProductRepo;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Example;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_CLASS;

@DataJpaTest
@TestPropertySource(properties = "spring.sql.init.mode=never") //not to load initial file
@Sql(scripts = {"/users.sql",
        "/orders.sql",
        "/products.sql",
        "/order_positions.sql"}, executionPhase = BEFORE_TEST_CLASS)
@DirtiesContext
class OrderPositionTest {

    @Autowired
    private OrderPositionRepo orderPositionRepo;
    @Autowired
    private OrderRepo orderRepo;
    @Autowired
    private ProductRepo productRepo;

    @Test
    public void emptyTestShouldSuccessfullyStartContext() {
    }

    @Test
    public void shouldNotDeleteOrderWhenDeletingOrderPosition() {
        //given
        OrderPosition orderPositionToDelete = orderPositionRepo.findById(1L).orElseThrow();
        List<OrderPosition> expectedRepoWithoutDeletedOrderPosition =
                orderPositionRepo.findAll().stream().filter(o -> !Objects.equals(o, orderPositionToDelete)).toList();
        List<Order> initialOrders = orderRepo.findAll();
        //when
        orderPositionRepo.delete(orderPositionToDelete);
        //then
        assertEquals(expectedRepoWithoutDeletedOrderPosition, orderPositionRepo.findAll());
        assertEquals(initialOrders, orderRepo.findAll());
    }

    @Test
    public void deleteOrderShouldDeleteAllAssociatedOrdersPositions() {
        //given
        Order orderToDelete = orderRepo.findById(1L).orElseThrow();
        List<OrderPosition> expectedRepoWithoutOrderPositionsOfDeletedOrder =
                orderPositionRepo.findAll().stream().filter(o -> !Objects.equals(o.getOrder(), orderToDelete)).toList();
        assumeFalse(expectedRepoWithoutOrderPositionsOfDeletedOrder.isEmpty());
        //when
        orderRepo.delete(orderToDelete);
        //then
        assertEquals(expectedRepoWithoutOrderPositionsOfDeletedOrder, orderPositionRepo.findAll());
    }

    @Test
    public void shouldNotDeleteProductWhenDeletingOrderPosition() {
        //given
        OrderPosition orderPositionToDelete = orderPositionRepo.findById(1L).orElseThrow();
        List<OrderPosition> expectedRepoWithoutDeletedOrderPosition =
                orderPositionRepo.findAll().stream().filter(o -> !Objects.equals(o, orderPositionToDelete)).toList();
        List<Product> initialProducts = productRepo.findAll();
        //when
        orderPositionRepo.delete(orderPositionToDelete);
        //then
        assertEquals(expectedRepoWithoutDeletedOrderPosition, orderPositionRepo.findAll());
        assertEquals(initialProducts, productRepo.findAll());
    }

    @Test
    public void deleteOProductShouldDeleteAllAssociatedOrdersPositions() {
        //given
        Product productToDelete = productRepo.findById(1L).orElseThrow();
        List<OrderPosition> expectedRepoWithoutOrderPositionsOfDeletedProduct =
                orderPositionRepo.findAll().stream().filter(o -> !Objects.equals(o.getProduct(), productToDelete))
                        .toList();
        assumeFalse(expectedRepoWithoutOrderPositionsOfDeletedProduct.isEmpty());
        //when
        productRepo.delete(productToDelete);
        //then
        assertEquals(expectedRepoWithoutOrderPositionsOfDeletedProduct, orderPositionRepo.findAll());
    }

    @Test
    public void successfullyAdded() {
        //given
        OrderPosition orderPositionToAdd = new OrderPosition(orderRepo.getReferenceById(2L),
                productRepo.getReferenceById(2L));
        orderPositionToAdd.setQuantity(1);
        assumeFalse(orderPositionRepo.exists(Example.of(orderPositionToAdd)));
        //when
        orderPositionRepo.save(orderPositionToAdd);
        //then
        assertEquals(orderPositionToAdd, orderPositionRepo.getReferenceById(orderPositionToAdd.getId()));
    }

    @Test
    @Disabled
    public void successfullyModified() { // wasnt able to clear cash so test is skipped
        //given
        OrderPosition orderPositionToAdd = orderPositionRepo.findById(1L).orElseThrow();
        orderPositionToAdd.setQuantity(orderPositionToAdd.getQuantity() + 1);
        clearCaches();
        OrderPosition orderPositionBeforeSettingQuantity = orderPositionRepo.findById(1L).orElseThrow();
        assumeFalse(Objects.equals(orderPositionBeforeSettingQuantity, orderPositionToAdd));
        //when
        orderPositionRepo.save(orderPositionToAdd);
        //then
        assertEquals(orderPositionToAdd, orderPositionRepo.getReferenceById(orderPositionToAdd.getId()));
    }

    public void clearCaches() {
    }

    @Test
    public void shouldNotAddDueToConstraintViolation() {
        //given
        OrderPosition existingOrderPosition = orderPositionRepo.findById(1L).orElseThrow();
        OrderPosition orderPositionToAdd = new OrderPosition(existingOrderPosition.getOrder(),
                existingOrderPosition.getProduct());
        orderPositionToAdd.setQuantity(1);
        //then
        assertThrows(DataIntegrityViolationException.class, () -> orderPositionRepo.save(orderPositionToAdd));
    }
}