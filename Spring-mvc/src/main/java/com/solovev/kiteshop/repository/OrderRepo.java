package com.solovev.kiteshop.repository;

import com.solovev.kiteshop.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface OrderRepo extends JpaRepository<Order, Long> {
    Collection<Order> getOrderByUser_Username(String userName);

    List<Order> getOrderByUser_UsernameAndStatus(String userName, Order.Status orderStatus);
}
