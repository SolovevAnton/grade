package com.solovev.beanconfiguration.bean;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(
        classes = BeanB.class
)
class BeanBTest {

    @Test
    void beanAInjectedInBeanB() {
        assertNotNull(beanA);
        assertEquals(beanA, beanB.getBeanA());
    }

    @Autowired
    private BeanB beanB;
    @MockitoBean
    private BeanA beanA;
}