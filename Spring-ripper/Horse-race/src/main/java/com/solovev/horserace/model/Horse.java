package com.solovev.horserace.model;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Data
public class Horse {
    private final String name;
    private final Breed breed;

    public Horse() {
        name = HorseFactory.generateHorseName();
        breed = HorseFactory.generateHorseBreed();
    }

    @RequiredArgsConstructor
    @Getter
    public enum Breed {
        TURKISH(10),
        AMERICAN(9),
        ARMENIAN(11);

        private final int maxSpeed;
    }

    public int getMaxSpeed() {
        return breed.getMaxSpeed();
    }

    @UtilityClass
    private static class HorseFactory {

        private static final Random rand = new Random();

        private static final List<String> horseNameFirstPart = List.of(
                "Good",
                "Bad",
                "Ugly",
                "Fast",
                "Blazing"
        );

        private static final List<String> horseNameSecondPart = List.of(
                "Cowboy",
                "Caballero",
                "Muchacho",
                "Queen"
        );

        private static Horse.Breed generateHorseBreed() {
            var breeds = Horse.Breed.values();
            return breeds[getRandom(breeds.length)];
        }

        private static String generateHorseName() {
            return horseNameFirstPart.get(getRandom(horseNameFirstPart.size())) + " " + horseNameSecondPart.get(
                    getRandom(horseNameSecondPart.size()));
        }

        private static int getRandom(int to) {
            return rand.nextInt(to);
        }
    }
}
