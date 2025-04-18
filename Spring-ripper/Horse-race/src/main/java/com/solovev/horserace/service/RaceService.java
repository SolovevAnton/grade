package com.solovev.horserace.service;

import com.solovev.horserace.model.Race;
import com.solovev.horserace.model.Rider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RaceService {
    public static final int RACE_DISTANCE = 50;
    private final HorseService horseService;

    public Race getRace() {
        var riders = horseService.getRiders();
        return new Race(RACE_DISTANCE, riders);
    }

    public String raceInfo(Race race) {
        StringBuilder sb = new StringBuilder("Race Info:\n");
        sb.append("distance: %d\n".formatted(race.getDistance()));
        race.getCompetitors().forEach(r -> sb.append(competitorInfo(r)));
        return sb.toString();
    }

    private String competitorInfo(Rider rider) {
        return "Rider %s: Horse %s\n".formatted(rider.getNumber(), rider.getHorse());
    }
}
