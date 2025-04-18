package com.solovev.beanconfiguration.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(
        classes = {AppProperties.class, AppProperties.BeanAConfig.class}
)
@EnableConfigurationProperties(AppProperties.class)
class AppPropertiesTest {

    @Test
    public void propertyBound() {
        //given
        var expectedName = "My first bean";
        //then
        assertEquals(expectedName, appProperties.getA().getName());
    }

    @Autowired
    private AppProperties appProperties;
}