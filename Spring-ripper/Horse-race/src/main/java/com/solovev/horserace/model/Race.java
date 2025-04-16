package com.solovev.horserace.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Race {
    private final Map<Rider, Horse> competitors = new HashMap<>();
}
