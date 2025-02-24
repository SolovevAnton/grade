package com.solovev.sevice;

import com.solovev.model.Book;
import lombok.NonNull;

import java.nio.file.Path;
import java.util.List;

public interface ParserXML {
    List<Book> parse(@NonNull Path xml);
}
