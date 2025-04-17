package com.solovev.horserace.model;

import lombok.Data;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Data
public class Rider {
    private static int counter = 1;
    private final int number;
    private final Horse horse;

    public Rider(Horse horse) {
        this.horse = horse;
        this.number = counter++;
    }
}
