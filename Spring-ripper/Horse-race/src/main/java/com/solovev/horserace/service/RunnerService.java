package com.solovev.horserace.service;

import com.solovev.horserace.model.Race;
import com.solovev.horserace.model.Rider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.Scanner;

@Service
@RequiredArgsConstructor
public class RunnerService implements CommandLineRunner {
    public static final String EXIT_STRING = "q";
    private final RaceService raceService;
    private final EmulationService emulationService;

    @Override
    public void run(String... args) throws Exception {
        Race race = raceService.getRace();
        System.out.println("Race is starting");
        System.out.println(raceService.raceInfo(race));
        System.out.println("Please make bet. Provide racer number to place a bet");
        try (Scanner scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                try {
                    int number = Integer.parseInt(line);
                    var foundRider = race.findByRiderId(number);
                    if (foundRider.isPresent()) {
                        Rider winner = emulationService.performRace(race);
                        System.out.println("Won rider: " + winner);
                        System.out.println("Your bet: " + (winner.equals(foundRider.get()) ? "won" : "lost"));
                        break;
                    } else {
                        notFoundRacer();
                    }
                } catch (NumberFormatException ignored) {
                    if (line.equalsIgnoreCase(EXIT_STRING)) {
                        break;
                    }
                    wrongNumberInput();
                }
            }
        }
    }


    private void notFoundRacer() {
        System.out.println("Race not found, please choose number from provided racers");
        System.out.println("If you want to exit print: q");
    }

    private void wrongNumberInput() {
        System.out.println("Invalid racer number, please enter a number");
        System.out.println("If you want to exit print: q");
    }

}
