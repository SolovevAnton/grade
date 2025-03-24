package com.solovev.kiteshop.repository;

import com.solovev.kiteshop.BaseDBRelatedConfig;
import com.solovev.kiteshop.model.product.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mapping.PropertyReferenceException;
import org.springframework.test.context.jdbc.Sql;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.BEFORE_TEST_CLASS;

@Sql(scripts = {"/products.sql"}, executionPhase = BEFORE_TEST_CLASS)
class ProductRepoTest extends BaseDBRelatedConfig {
    @Autowired
    private ProductRepo productRepo;

    @Test
    public void sortOfProductionYearDesc_expectedSortedInstance() {
        //given
        Sort sortProductionYear = Sort.by("productionYear").descending();
        Collection<Product> expectedSortedProducts =
                productRepo.findAll().stream().sorted(Comparator.comparing(Product::getProductionYear).reversed())
                        .toList();
        //when
        List<Product> sortedProducts = productRepo.findAll(sortProductionYear);
        //then
        assertEquals(expectedSortedProducts, sortedProducts);
    }

    @Test
    public void sortWithCorruptedParameters_expectedSortedInstance() {
        //given
        Sort corruptedSortInstance = Sort.by("nonExistingField").descending();
        //when
        assertThrows(PropertyReferenceException.class, () -> productRepo.findAll(corruptedSortInstance));
    }
}