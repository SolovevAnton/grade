package com.solovev.horserace.model;

import lombok.Data;

import java.util.Collection;

@Data
public class Race {
    private final int distance;
    private final Collection<Rider> competitors;
    private Rider winner;


    public synchronized boolean tryToSetWinner(Rider rider) {
        if (winner == null) {
            winner = rider;
            return true;
        }
        return false;
    }
}

