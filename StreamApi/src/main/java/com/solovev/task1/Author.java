package com.solovev.task1;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
public class Author {
    private String name;
    private short age;
    @ToString.Exclude
    private List<Book> books;
}
