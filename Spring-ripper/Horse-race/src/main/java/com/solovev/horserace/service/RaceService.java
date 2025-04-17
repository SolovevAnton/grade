package com.solovev.horserace.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RaceService {
    private final HorseService horseService;

}
