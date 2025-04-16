package com.solovev.horserace.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

public record Horse(
        String name,
        Breed breed
) {

    @RequiredArgsConstructor
    @Getter
    public enum Breed {
        TURKISH(10),
        AMERICAN(9),
        ARMENIAN(11);
        private final int maxSpeed;
    }
}
