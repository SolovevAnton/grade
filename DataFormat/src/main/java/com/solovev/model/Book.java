package com.solovev.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class Book {
    private final String name;
    private final LocalDate publishDate;
    private final List<Author> authors;
    private final List<Publisher> publishers;
}
