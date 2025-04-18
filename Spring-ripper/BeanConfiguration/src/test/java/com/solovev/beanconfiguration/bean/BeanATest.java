package com.solovev.beanconfiguration.bean;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(
        classes = BeanA.class
)
class BeanATest {

    @Test
    void propertySetTest() {
        //given
        var expectedName = "My first bean";
        //then
        assertEquals(expectedName, beanA.getName());
    }

    @Autowired
    private BeanA beanA;
}