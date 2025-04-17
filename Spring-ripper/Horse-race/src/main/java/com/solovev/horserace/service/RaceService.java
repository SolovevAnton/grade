package com.solovev.horserace.service;

import com.solovev.horserace.model.Race;
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
}
