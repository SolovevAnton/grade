package com.solovev.sevice;

import com.solovev.model.Author;
import com.solovev.model.Book;
import com.solovev.model.Publisher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public abstract class ParserXMLBaseTest {
    private ParserXML parserXML;


    @Test
    public void parseXMLHappyPath() {
        //given
        Path file = Paths.get("src/test/resources/books.xml");
        assertTrue(Files.exists(file));
        //when
        List<Book> books = parserXML.parse(file);
        //then
        assertEquals(getExpectedBooks(), books);
    }


    @Test
    public void throwsForNonExistentFile() {
        //given
        Path file = Paths.get("nonexistentfile.xml");
        assertFalse(Files.exists(file));
        //then
        assertThrows(IllegalArgumentException.class, () -> parserXML.parse(file));
    }



    @BeforeEach
    public void setUp() {
        parserXML = provideParserXML();
    }

    protected abstract ParserXML provideParserXML();

    private List<Book> getExpectedBooks() {
        Author author1 = new Author("Author1 Name", "Author1 Surname");
        Publisher publisher1 = new Publisher("Publisher1 Name", "publisher1@example.com");
        Book book1 = new Book(
                "Book 1",
                LocalDate.of(2025, 1, 1),
                Arrays.asList(author1),
                Arrays.asList(publisher1)
        );

        // Book 2
        Author author2 = new Author("Author2 Name", "Author2 Surname");
        Author author3 = new Author("Author3 Name", "Author3 Surname");
        Publisher publisher2 = new Publisher("Publisher2 Name", "publisher2@example.com");
        Book book2 = new Book(
                "Book 2",
                LocalDate.of(2025, 2, 1),
                Arrays.asList(author2, author3),
                Arrays.asList(publisher2)
        );

        // Book 3
        Author author4 = new Author("Author4 Name", "Author4 Surname");
        Author author5 = new Author("Author5 Name", "Author5 Surname");
        Publisher publisher3 = new Publisher("Publisher3 Name", "publisher3@example.com");
        Publisher publisher4 = new Publisher("Publisher4 Name", "publisher4@example.com");
        Book book3 = new Book(
                "Book 3",
                LocalDate.of(2025, 3, 1),
                Arrays.asList(author4, author5),
                Arrays.asList(publisher3, publisher4)
        );

        return Arrays.asList(book1, book2, book3);
    }
}
