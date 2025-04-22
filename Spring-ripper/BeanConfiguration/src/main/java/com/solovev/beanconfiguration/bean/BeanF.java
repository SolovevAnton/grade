package com.solovev.beanconfiguration.bean;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class BeanF {
    private String name;

    public BeanF() {
        log.info("BeanF constructor");
    }

    @Autowired
    public void setName(@Value("beanF name") String name) {
        this.name = name;
        log.info("BeanF setName");
    }

    @PostConstruct
    public void init() {
        log.info("BeanF init post construct");
    }

    @PreDestroy
    public void destroy() {
        log.info("BeanF destroy");
    }
}
