package com.solovev.kiteshop.repository;

import com.solovev.kiteshop.model.order.Order;
import com.solovev.kiteshop.model.order.OrderPosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface OrderPositionRepo extends JpaRepository<OrderPosition, Long> {
    Collection<OrderPosition> getAllByOrder(Order order);
}
