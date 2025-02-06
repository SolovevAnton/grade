package com.solovev.task2;

import lombok.Data;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import static java.util.stream.Collector.Characteristics.CONCURRENT;
import static java.util.stream.Collector.Characteristics.UNORDERED;

public class MyCollector implements Collector<SomeEntity, MyCollector.Accumulator, SomeEntityStatistics> {


    @Override
    public Supplier<Accumulator> supplier() {
        return Accumulator::new;
    }

    @Override
    public BiConsumer<Accumulator, SomeEntity> accumulator() {
        return Accumulator::addEntity;
    }

    @Override
    public BinaryOperator<Accumulator> combiner() {
        return Accumulator::combine;
    }

    @Override
    public Function<Accumulator, SomeEntityStatistics> finisher() {
        return Accumulator::toStatistics;
    }

    @Override
    public Set<Characteristics> characteristics() {
        return Set.of(UNORDERED, CONCURRENT);
    }

    @Data
    public class Accumulator {
        private final AtomicLong numberOfEntries = new AtomicLong();
        private final AtomicLong sumNameLength = new AtomicLong();
        private final AtomicLong sumAge = new AtomicLong();
        private final AtomicLong maxAge = new AtomicLong();
        private Map<Property, Map<Boolean, Integer>> numberOfTrueAndFalseForEachProperty = new ConcurrentHashMap<>();

        private void addProperty(Property property, boolean value) {
            numberOfTrueAndFalseForEachProperty.merge(property, getMap(value),
                    (old, _) -> addStatistics(old, value));
        }

        private Map<Boolean, Integer> getMap(boolean key) {
            Map<Boolean, Integer> map = new ConcurrentHashMap<>();
            map.put(key, 1);
            return map;
        }

        private Map<Boolean, Integer> addStatistics(Map<Boolean, Integer> stats, boolean key) {
            stats.merge(key, 1, Integer::sum);
            return stats;
        }

        private void addAge(long age) {
            sumAge.addAndGet(age);
            maxAge.getAndUpdate(ma -> Math.max(ma, age));
        }

        private void addNameLength(long nameLength) {
            sumNameLength.addAndGet(nameLength);
        }

        public void addEntity(SomeEntity entity) {
            numberOfEntries.incrementAndGet();
            addAge(entity.getAge());
            addNameLength(entity.getName().length());
            entity.getProperties().forEach(this::addProperty);
        }

        public SomeEntityStatistics toStatistics() {

            long avgAge = 0;
            long avgNameLength = 0;
            long count = numberOfEntries.get();
            if (count != 0) {
                avgAge = sumAge.get() / count;
                avgNameLength = sumNameLength.get() / count;
            }
            return new SomeEntityStatistics(
                    avgNameLength,
                    avgAge,
                    maxAge.get(),
                    Collections.unmodifiableMap(numberOfTrueAndFalseForEachProperty)
            );
        }

        public Accumulator combine(Accumulator other) {
            this.numberOfEntries.addAndGet(other.numberOfEntries.get());
            this.sumNameLength.addAndGet(other.sumNameLength.get());
            this.sumAge.addAndGet(other.sumAge.get());
            this.maxAge.updateAndGet(ma -> Math.max(ma, other.maxAge.get()));
            combine(other.numberOfTrueAndFalseForEachProperty);
            return this;
        }

        private void combine(Map<Property, Map<Boolean, Integer>> other) {
            other.forEach((p, s) -> numberOfTrueAndFalseForEachProperty.merge(p, s, this::combine));
        }

        private Map<Boolean, Integer> combine(Map<Boolean, Integer> old, Map<Boolean, Integer> newValue) {
            newValue.forEach((k, v) -> old.merge(k, v, Integer::sum));
            return old;
        }
    }
}
