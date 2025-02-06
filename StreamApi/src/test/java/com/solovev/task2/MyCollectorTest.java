package com.solovev.task2;

import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MyCollectorTest {
    @Test
    void testCollectorWithMultipleEntities() {
        SomeEntity entity1 = new SomeEntity("Alice", 25L, Map.of(Property.PROP1, true, Property.PROP2, false));
        SomeEntity entity2 = new SomeEntity("Bob", 30L, Map.of(Property.PROP1, false, Property.PROP3, true));
        SomeEntity entity3 = new SomeEntity("Charlie", 35L, Map.of(Property.PROP2, true, Property.PROP4, false));

        SomeEntityStatistics stats = Stream.of(entity1, entity2, entity3)
                .collect(new MyCollector());

        assertEquals(5, stats.avgNameLength());  // ("Alice", "Bob", "Charlie") -> avg length: (5+3+7)/3 = 5
        assertEquals(30, stats.avgAge());  // (25+30+35)/3 = 30
        assertEquals(35, stats.maxAge()); // max age = 35

        assertEquals(Map.of(
                Property.PROP1, Map.of(true, 1, false, 1),
                Property.PROP2, Map.of(false, 1, true, 1),
                Property.PROP3, Map.of(true, 1),
                Property.PROP4, Map.of(false, 1)
        ), stats.numberOfTrueAndFalseForEachProperty());
    }

    @Test
    void testCollectorWithZeroElements() {
        SomeEntityStatistics stats = Stream.<SomeEntity>empty()
                .collect(new MyCollector());

        assertEquals(0, stats.avgNameLength());
        assertEquals(0, stats.avgAge());
        assertEquals(0, stats.maxAge());
        assertTrue(stats.numberOfTrueAndFalseForEachProperty().isEmpty());
    }

    @Test
    void testCollectorWithSameAge() {
        SomeEntity entity1 = new SomeEntity("John", 40L, Map.of(Property.PROP1, true));
        SomeEntity entity2 = new SomeEntity("Jane", 40L, Map.of(Property.PROP2, true));
        SomeEntity entity3 = new SomeEntity("Jake", 40L, Map.of(Property.PROP3, false));

        SomeEntityStatistics stats = Stream.of(entity1, entity2, entity3)
                .collect(new MyCollector());

        assertEquals(4, stats.avgNameLength()); // avg(4,4,4) = 4
        assertEquals(40, stats.avgAge()); // avg(40,40,40) = 40
        assertEquals(40, stats.maxAge()); // max = 40
    }
}
