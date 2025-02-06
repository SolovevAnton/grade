package com.solovev.task2;

import lombok.Data;

import java.util.Map;

@Data
public class SomeEntity {
    private final String name;
    private final Long age;
    private final Map<Property, Boolean> properties;
}
