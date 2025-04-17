package com.solovev.horserace.service;

import com.solovev.horserace.model.Rider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class HorseService {
    public static final int RIDER_COUNT = 5;


    public Set<Rider> getRiders() {
        return IntStream.range(0, RIDER_COUNT).mapToObj(__ -> getRider()).collect(Collectors.toSet());
    }

    @Lookup
    protected Rider getRider() {
        return null;
    }

}
