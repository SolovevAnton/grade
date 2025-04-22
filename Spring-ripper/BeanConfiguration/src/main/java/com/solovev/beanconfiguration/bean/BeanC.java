package com.solovev.beanconfiguration.bean;

import lombok.Data;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.stereotype.Component;

@Component
@Data
public class BeanC {
    private final ObjectFactory<BeanD> prototypeBeanD;

    public BeanD getPrototypeBeanD() {
        return prototypeBeanD.getObject();
    }
}
