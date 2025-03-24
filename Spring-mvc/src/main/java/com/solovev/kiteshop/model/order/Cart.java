package com.solovev.kiteshop.model.order;

import com.solovev.kiteshop.common.ValidationErrorsMessages;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class Cart {
    @NotEmpty(message = ValidationErrorsMessages.CART_NOT_EMPTY)
    private final Map<Long, Integer> products = new ConcurrentHashMap<>();

    public void addItem(Long productId, int quantity) {
        products.merge(productId, quantity, (prev, current) -> prev + quantity);
    }

    public int getQuantity(long productId) {
        return products.getOrDefault(productId, 0);
    }

    public Collection<Long> getIds() {
        return products.keySet();
    }

}
