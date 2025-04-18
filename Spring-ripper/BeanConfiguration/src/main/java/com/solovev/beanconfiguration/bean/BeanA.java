package com.solovev.beanconfiguration.bean;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class BeanA {
    private String name;

    @Autowired
    public void setName(@Value("${application.bean.a.name}") String name) {
        this.name = name;
    }
}
