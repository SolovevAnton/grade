package com.solovev.horserace.service;

import com.solovev.horserace.model.Horse;
import com.solovev.horserace.model.Rider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(
        classes = {HorseService.class, Rider.class, Horse.class}
)
class HorseServiceTest {

    @Test
    public void fiveRidersCreated() {
        //given
        var riders = horseService.getRiders();
        assertThat(riders)
                .hasSize(HorseService.RIDER_COUNT)
                .noneMatch(Objects::isNull);
    }

    @Autowired
    private HorseService horseService;
}