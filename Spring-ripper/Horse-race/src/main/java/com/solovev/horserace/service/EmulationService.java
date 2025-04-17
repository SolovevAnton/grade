package com.solovev.horserace.service;

import com.solovev.horserace.model.Race;
import com.solovev.horserace.model.Rider;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmulationService {
    private final Random rand = new Random();
    private static final int PROGRESS_BAR_WIDTH = 50;

    @SneakyThrows
    public Rider performRace(Race race) {
        var runnables = race
                .getCompetitors()
                .stream()
                .map(r -> new Thread(() -> emulateLine(race, new RaceLine(race.getDistance(), r))))
                .toList();
        runnables.forEach(Thread::start);
        runnables.forEach(this::join);
        return race.getWinner();
    }

    @SneakyThrows
    private void join(Thread thread) {
        thread.join();
    }

    @SneakyThrows
    private void emulateLine(Race race, RaceLine line) {
        while (!line.isCompleted()) {
            int traveledDistance = traveledDistance(line.rider);
            line.addDistance(traveledDistance);
            updateConsoleLine(line);
            Thread.sleep(100);
        }
        race.tryToSetWinner(line.rider);
    }

    private int traveledDistance(Rider rider) {
        return rand.nextInt(1, rider.getHorse().getMaxSpeed());
    }

    private void updateConsoleLine(RaceLine line) {
        // Build the output string
        String progressBar = line.buildProgressBar(PROGRESS_BAR_WIDTH);
        String output = String.format("Rider %-2d|| %s ||", // Pad rider name
                line.rider.getNumber(),
                progressBar);
        System.out.println(output);
    }

    private class RaceLine {
        private static int allLines = 1;
        private final int lineNumber;
        private final int totalDistance;
        private final Rider rider;
        private int currentDistance;

        public RaceLine(int totalDistance, Rider rider) {
            this.lineNumber = allLines++;
            this.totalDistance = totalDistance;
            this.rider = rider;
        }

        boolean isCompleted() {
            return totalDistance <= this.currentDistance;
        }

        void addDistance(int distance) {
            this.currentDistance += distance;
        }

        String buildProgressBar(int barWidth) {
            double progress = (totalDistance <= 0) ? 0.0 : (double) currentDistance / totalDistance;
            progress = Math.min(1.0, Math.max(0.0, progress)); // Clamp progress between 0.0 and 1.0

            int filledChars = (int) (progress * barWidth);
            int emptyChars = barWidth - filledChars;

            return "=".repeat(filledChars) +
                    " ".repeat(emptyChars);
        }
    }
}

