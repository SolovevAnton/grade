package com.solovev.beanpostprocess.bean;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest(
)
class MyBeanTest {

    @Test
    void testBean() {
        //then
        assertThat(myBean.getEmptyString()).isNotNull().isEmpty();
        assertEquals(MyBean.TO_INJECT, myBean.getOneString());
        assertThat(myBean.getSomeString()).isNotNull().isNotEmpty();
        assertNull(myBean.getNotAnnotated());
    }

    @Autowired
    private MyBean myBean;
}