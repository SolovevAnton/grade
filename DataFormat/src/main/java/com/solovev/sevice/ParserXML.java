package com.solovev.sevice;

import com.solovev.model.Book;

import java.nio.file.Path;
import java.util.List;

public interface ParserXML {
    List<Book> parse(Path xml);
}
