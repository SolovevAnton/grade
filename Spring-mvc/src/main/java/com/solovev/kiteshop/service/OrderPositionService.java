package com.solovev.kiteshop.service;

import com.solovev.kiteshop.model.order.Cart;
import com.solovev.kiteshop.model.order.Order;
import com.solovev.kiteshop.model.order.OrderPosition;
import com.solovev.kiteshop.model.product.Product;
import com.solovev.kiteshop.repository.OrderPositionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class OrderPositionService {
    private final OrderPositionRepo orderPositionRepo;
    private final ProductService productService;

    @Transactional(readOnly = true)
    public Collection<OrderPosition> getAllByOrder(Order order) {
        return orderPositionRepo.getAllByOrder(order);
    }

    @Transactional(readOnly = true)
    public Collection<OrderPosition> getAll() {
        return orderPositionRepo.findAll();
    }

    @Transactional
    public void createAndAddPositionsFromCart(Order order, Cart cart) {
        var positions = getAllByOrder(order);
        productService
                .get(cart)
                .forEach((product, addedQuantity) ->
                        orderPositionRepo.save(createOrModifyPosition(positions, order, product, addedQuantity)));
    }

    private OrderPosition createOrModifyPosition(Collection<OrderPosition> positions, Order order, Product product,
                                                 int addedQuantity) {
        OrderPosition foundOrCreatedPosition = positions
                .stream()
                .filter(op -> op.getProduct().equals(product))
                .findFirst().orElse(new OrderPosition(order, product));
        foundOrCreatedPosition.addQuantity(addedQuantity);
        return foundOrCreatedPosition;
    }
}
