package com.solovev.task1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
public class Book {
    private String title;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Author> authors;
    private int numberOfPages;
}
