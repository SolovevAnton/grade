package com.solovev.kiteshop.model;

import com.solovev.kiteshop.BaseDBRelatedConfig;
import com.solovev.kiteshop.model.product.Product;
import com.solovev.kiteshop.repository.ProductRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Example;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_CLASS;

@Sql(scripts = {"/products.sql"}, executionPhase = BEFORE_TEST_CLASS)
class ProductTest extends BaseDBRelatedConfig {
    @Autowired
    private ProductRepo productRepo;

    @Test
    public void emptyTestShouldSuccessfullyStartContext() {
    }

    @Test
    public void shouldAddSuccessfully() {
        //given
        Product existingProduct = productRepo.findById(1L).orElseThrow();
        Product productToAdd = new Product("modelToAdd", existingProduct.getPrice(), existingProduct.getSize(),
                existingProduct.getProductionYear(), existingProduct.getBrand());
        assumeFalse(productRepo.exists(Example.of(productToAdd)));
        //when
        productRepo.saveAndFlush(productToAdd);
        //then
        assertEquals(productToAdd, productRepo.findById(productToAdd.getId()).get());
    }

    @Test
    public void shouldNotAddSuccessfullyDueToConstraintViolation() {
        //given
        Product existingProduct = productRepo.findById(1L).orElseThrow();
        Product productToAdd = new Product(existingProduct.getModel(), existingProduct.getPrice(),
                existingProduct.getSize(),
                existingProduct.getProductionYear(), existingProduct.getBrand());
        //then
        assertThrows(DataAccessException.class, () -> productRepo.save(productToAdd));
    }

}