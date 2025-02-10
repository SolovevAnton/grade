package com.solovev;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MyListTest {

    @Nested
    public class MapMethod {
        @Test
        void testMapWithMultiplication() {
            MyList<Integer> numbers = new MyList<>();
            numbers.addAll(List.of(1, 2, 3, 4));

            List<Integer> result = numbers.map(x -> x * 2);

            assertEquals(List.of(2, 4, 6, 8), result);
        }

        @Test
        void testMapWithToStringConversion() {
            MyList<Integer> numbers = new MyList<>();
            numbers.addAll(List.of(1, 2, 3, 4));

            List<String> result = numbers.map(Object::toString);

            assertEquals(List.of("1", "2", "3", "4"), result);
        }

        @Test
        void testMapWithEmptyList() {
            MyList<Integer> numbers = new MyList<>();

            List<Integer> result = numbers.map(x -> x * 2);

            assertTrue(result.isEmpty());
        }

        @Test
        void testMapWithDifferentType() {
            MyList<Double> numbers = new MyList<>();
            numbers.addAll(List.of(1.5, 2.5, 3.5));

            List<String> result = numbers.map(String::valueOf);

            assertEquals(List.of("1.5", "2.5", "3.5"), result);
        }

        @Test
        void testMapWithStrings() {
            MyList<String> words = new MyList<>();
            words.addAll(List.of("a", "bb", "ccc"));

            List<Integer> result = words.map(String::length);

            assertEquals(List.of(1, 2, 3), result);
        }
    }

    @Nested
    public class ReduceMethod {

        @Test
        void testReduceWithSum() {
            MyList<Integer> numbers = new MyList<>();
            numbers.addAll(List.of(1, 2, 3, 4));

            Integer result = numbers.reduce(Integer::sum);

            assertEquals(10, result);
        }

        @Test
        void testReduceWithSumOneElement() {
            MyList<Integer> numbers = new MyList<>();
            numbers.add(1);

            Integer result = numbers.reduce(Integer::sum);

            assertEquals(1, result);
        }

        @Test
        void testReduceWithMultiplication() {
            MyList<Integer> numbers = new MyList<>();
            numbers.addAll(List.of(1, 2, 3, 4));

            Integer result = numbers.reduce((x, y) -> x * y);

            assertEquals(24, result);
        }

        @Test
        void testReduceWithStringConcatenation() {
            MyList<String> words = new MyList<>();
            words.addAll(List.of("a", "b", "c"));

            String result = words.reduce((x, y) -> x + y);

            assertEquals("abc", result);
        }

        @Test
        void testReduceOnEmptyList() {
            MyList<Integer> numbers = new MyList<>();

            Integer result = numbers.reduce(Integer::sum);

            assertNull(result);
        }

        @Test
        void testReduceToFindMax() {
            MyList<Integer> numbers = new MyList<>();
            numbers.addAll(List.of(3, 7, 2, 9, 5));

            Integer max = numbers.reduce(Math::max);

            assertEquals(9, max);
        }
    }
}