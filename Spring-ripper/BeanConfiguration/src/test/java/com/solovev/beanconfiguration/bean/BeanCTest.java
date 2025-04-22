package com.solovev.beanconfiguration.bean;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(
        classes = {BeanC.class, BeanD.class}
)
class BeanCTest {

    @Test
    void prototypeShouldBeNewEveryTime() {
        assertEquals(0, beanC.getPrototypeBeanD().getId());
        assertEquals(1, beanC.getPrototypeBeanD().getId());
    }

    @Autowired
    private BeanC beanC;
}