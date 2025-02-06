package com.solovev.task2;

import java.util.Map;

/**
 * This class withh hold statistics for SomeEntity
 **/
public record SomeEntityStatistics(long avgNameLength, long avgAge, long maxAge,
                                   Map<Property, Map<Boolean, Integer>> numberOfTrueAndFalseForEachProperty) {
    public void put(Property value, Map<Boolean, Integer> statistics) {
        numberOfTrueAndFalseForEachProperty.put(value, statistics);
    }

}
