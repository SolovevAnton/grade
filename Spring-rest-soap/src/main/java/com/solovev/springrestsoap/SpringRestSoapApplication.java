package com.solovev.springrestsoap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class SpringRestSoapApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringRestSoapApplication.class, args);
    }

}

