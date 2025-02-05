package com.solovev.task3;


import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

public class Words {
    public static final int SKIP_RARER_THEN_COUNT = 10;
    public static final String FINAL_DELIMITER = "\n";

    private final Comparator<Map.Entry<String, Long>> entryComparator = Comparator
            .comparingLong((Map.Entry<String, Long> e) -> e.getValue())
            .reversed()
            .thenComparing(Map.Entry::getKey);

    private final Function<Map.Entry<String, Long>, String> entryToString = e -> String.format("%s - %s", e.getKey(), e.getValue());

    private Stream<String> safeExtractWords(String line) {

        final String REGEX_MATHCER = "[\\w[А-яё]&&[\\D]]{4,}";
        try {
            return Pattern.compile(REGEX_MATHCER)
                    .matcher(line)
                    .results()
                    .map(MatchResult::group);

        } catch (NullPointerException e) {
            return null;
        }
    }


    public String countWords(List<String> lines) {
        return lines
                .stream()
                .flatMap(this::safeExtractWords)
                .map(String::toLowerCase)
                .collect(groupingBy(Function.identity(), counting()))
                .entrySet()
                .stream()
                .filter(e -> e.getValue() >= SKIP_RARER_THEN_COUNT)
                .sorted(entryComparator)
                .map(entryToString)
                .collect(Collectors.joining(FINAL_DELIMITER));
    }

}
