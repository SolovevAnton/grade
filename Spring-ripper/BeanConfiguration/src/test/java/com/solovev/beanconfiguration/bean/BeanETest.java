package com.solovev.beanconfiguration.bean;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ImportResource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@EnableAutoConfiguration
@SpringBootTest(
        classes = {}
)
@ImportResource("applicationContext.xml")
class BeanETest {

    @Test
    void methodSuccessfullyReplacedTest() {
        assertNotNull(beanE.methodToReplace());
        assertEquals(MethodReplacerImpl.REPLACEMENT_STRING, beanE.methodToReplace());
    }

    @Autowired
    private BeanE beanE;
}