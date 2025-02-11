package com.solovev.task1;

import lombok.experimental.UtilityClass;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

@UtilityClass
public class CollectionToStream {
    //a
    public static boolean booksContainsOver200Pages(Book[] books) {
        return Stream.of(books).map(Book::getNumberOfPages).anyMatch(i -> i > 200);
    }

    //b
    public static List<Book> booksWithMaxPages(Book[] books) {
        int maxPages = Stream.of(books).mapToInt(Book::getNumberOfPages).max().orElseThrow();
        return Stream.of(books).filter(b -> b.getNumberOfPages() == maxPages).toList();
    }

    public static List<Book> booksWithMinPages(Book[] books) {
        int maxPages = Stream.of(books).mapToInt(Book::getNumberOfPages).min().orElseThrow();
        return Stream.of(books).filter(b -> b.getNumberOfPages() == maxPages).toList();
    }

    //c
    public static List<Book> booksWithOneAuthor(Book[] books) {
        return Stream.of(books).filter(b -> b.getAuthors().size() == 1).toList();
    }

    //d
    public static List<Book> sortedByPageNum(Book[] books) {
        return Stream.of(books).sorted(Comparator.comparingInt(Book::getNumberOfPages)).toList();
    }

    public static List<Book> sortedByPageName(Book[] books) {
        return Stream.of(books).sorted(Comparator.comparing(Book::getTitle)).toList();
    }

    //e + f
    public static List<String> getAndPrintAllTitles(Book[] books) {
        return Stream.of(books).map(Book::getTitle).peek(System.out::println).toList();
    }


    //g
    public static Collection<Author> getAllAuthors(Book[] books) {
        return Stream.of(books).map(Book::getAuthors).flatMap(List::stream).collect(toSet());
    }

    //5
    public static Map<Author, Optional<String>> bookWithMaxPagesForAllAuthors(Author[] authors) {
        return Stream.of(authors)
                .collect(
                        groupingBy(
                                Function.identity(),
                                collectingAndThen(
                                        flatMapping(author -> author.getBooks().stream(),
                                                maxBy(Comparator.comparingInt(Book::getNumberOfPages))
                                        ),
                                        o -> o.map(Book::getTitle)
                                )
                        )
                );
    }

    public static Optional<String> bookTitleWithMaxPagesForAuthor(Author author) {
        return author.getBooks()
                .stream()
                .max(Comparator.comparingInt(Book::getNumberOfPages))
                .map(Book::getTitle);
    }

}
