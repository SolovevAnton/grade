package com.solovev.kiteshop.service;

import com.solovev.kiteshop.model.order.Cart;
import com.solovev.kiteshop.model.product.Product;
import com.solovev.kiteshop.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepo productRepo;

    @Transactional(readOnly = true)
    public List<Product> get() {
        return productRepo.findAll();
    }

    @Transactional(readOnly = true)
    public Page<Product> get(Pageable pageable) {
        return productRepo.findAll(pageable);
    }

    /**
     * @param cart to getProductsFrom
     * @return all products from cart IDs IGNORES id that wasn't found
     */
    @Transactional(readOnly = true)
    public Map<Product, Integer> get(Cart cart) {
        return productRepo
                .findAllById(cart.getIds())
                .stream()
                .collect(Collectors.toMap(Function.identity(),
                        p -> cart.getQuantity(p.getId())));
    }
}
