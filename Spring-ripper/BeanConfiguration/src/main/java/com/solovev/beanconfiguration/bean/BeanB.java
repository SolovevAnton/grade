package com.solovev.beanconfiguration.bean;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BeanB {
    @Getter
    private final BeanA beanA;
}
