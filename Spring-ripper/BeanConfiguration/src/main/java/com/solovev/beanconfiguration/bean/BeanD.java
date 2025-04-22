package com.solovev.beanconfiguration.bean;

import lombok.Data;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Data
public class BeanD {
    private static int counter;
    private final int id;

    public BeanD() {
        this.id = counter++;
    }
}
