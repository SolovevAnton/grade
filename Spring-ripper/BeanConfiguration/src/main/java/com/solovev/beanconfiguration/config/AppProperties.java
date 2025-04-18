package com.solovev.beanconfiguration.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("application.bean")
@Data
public class AppProperties {
    private final BeanAConfig a;

    @Data
    public static class BeanAConfig {
        private String name;
    }
}
