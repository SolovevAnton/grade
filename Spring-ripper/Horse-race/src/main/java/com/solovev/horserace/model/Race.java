package com.solovev.horserace.model;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class Race {
    private final int distance;
    private final Map<Rider, Horse> competitors = new HashMap<>();

    public void addCompetitor(Rider rider, Horse horse) {
        competitors.put(rider, horse);
    }

}
