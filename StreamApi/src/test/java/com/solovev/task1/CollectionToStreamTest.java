package com.solovev.task1;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.*;

import static com.solovev.task1.CollectionToStream.*;
import static org.junit.jupiter.api.Assertions.*;

class CollectionToStreamTest {

    @Nested
    class BookPageCountTest {

        @Test
        void shouldReturnTrueWhenAllBooksHaveMoreThan200Pages() {
            Book book1 = new Book("Book One", List.of(new Author("Alice", (short) 45, null)), 250);
            Book book2 = new Book("Book Two", List.of(new Author("Bob", (short) 50, null)), 300);
            Book book3 = new Book("Book Three", List.of(new Author("Charlie", (short) 35, null)), 400);

            Book[] books = {book1, book2, book3};


            assertTrue(booksContainsOver200Pages(books));
        }

        @Test
        void shouldReturnFalseWhenNoBooksHaveMoreThan200Pages() {
            Book book1 = new Book("Book One", List.of(new Author("Alice", (short) 45, null)), 150);
            Book book2 = new Book("Book Two", List.of(new Author("Bob", (short) 50, null)), 180);
            Book book3 = new Book("Book Three", List.of(new Author("Charlie", (short) 35, null)), 199);

            Book[] books = {book1, book2, book3};


            assertFalse(booksContainsOver200Pages(books));
        }

        @Test
        void shouldReturnTrueWhenOnlyOneBookHasMoreThan200Pages() {
            Book book1 = new Book("Book One", List.of(new Author("Alice", (short) 45, null)), 150);
            Book book2 = new Book("Book Two", List.of(new Author("Bob", (short) 50, null)), 220);
            Book book3 = new Book("Book Three", List.of(new Author("Charlie", (short) 35, null)), 180);

            Book[] books = {book1, book2, book3};

            assertTrue(booksContainsOver200Pages(books));
        }
    }

    @Nested
    class BookMaxMinTest {

        @Test
        void shouldReturnBooksWithMaxPages() {
            Book book1 = new Book("Book One", List.of(new Author("Alice", (short) 45, null)), 150);
            Book book2 = new Book("Book Two", List.of(new Author("Bob", (short) 50, null)), 300);
            Book book3 = new Book("Book Three", List.of(new Author("Charlie", (short) 35, null)), 300);
            Book book4 = new Book("Book Four", List.of(new Author("David", (short) 40, null)), 200);

            Book[] books = {book1, book2, book3, book4};

            List<Book> result = booksWithMaxPages(books);

            assertEquals(2, result.size());
        }

        @Test
        void shouldReturnBooksWithMinPages() {
            Book book1 = new Book("Book One", List.of(new Author("Alice", (short) 45, null)), 150);
            Book book2 = new Book("Book Two", List.of(new Author("Bob", (short) 50, null)), 300);
            Book book3 = new Book("Book Three", List.of(new Author("Charlie", (short) 35, null)), 120);
            Book book4 = new Book("Book Four", List.of(new Author("David", (short) 40, null)), 120);

            Book[] books = {book1, book2, book3, book4};

            List<Book> result = booksWithMinPages(books);
            assertEquals(2, result.size());
        }

        @Test
        void shouldReturnSingleBookWhenOnlyOneHasMaxPages() {
            Book book1 = new Book("Book One", List.of(new Author("Alice", (short) 45, null)), 150);
            Book book2 = new Book("Book Two", List.of(new Author("Bob", (short) 50, null)), 180);
            Book book3 = new Book("Book Three", List.of(new Author("Charlie", (short) 35, null)), 220);

            Book[] books = {book1, book2, book3};

            List<Book> result = booksWithMaxPages(books);

            assertEquals(1, result.size());
        }

        @Test
        void shouldReturnSingleBookWhenOnlyMinPages() {
            Book book1 = new Book("Book One", List.of(new Author("Alice", (short) 45, null)), 100);
            Book book2 = new Book("Book Two", List.of(new Author("Bob", (short) 50, null)), 150);
            Book book3 = new Book("Book Three", List.of(new Author("Charlie", (short) 35, null)), 220);

            Book[] books = {book1, book2, book3};

            List<Book> result = booksWithMinPages(books);

            assertEquals(1, result.size());
        }

        @Test
        void shouldReturnBooksWithOneAuthor() {
            Book book1 = new Book("Book One", List.of(new Author("Alice", (short) 45, null)), 150);
            Book book2 = new Book("Book Two", List.of(new Author("Bob", (short) 50, null), new Author("Charlie", (short) 35, null)), 300);
            Book book3 = new Book("Book Three", List.of(new Author("David", (short) 40, null)), 200);

            Book[] books = {book1, book2, book3};

            List<Book> result = booksWithOneAuthor(books);

            assertTrue(result.containsAll(List.of(book1, book3)));
        }
        @Test
        void shouldSortBooksByPageNumber() {
            Book book1 = new Book("Book One", List.of(new Author("Alice", (short) 45, null)), 150);
            Book book2 = new Book("Book Two", List.of(new Author("Bob", (short) 50, null)), 300);
            Book book3 = new Book("Book Three", List.of(new Author("David", (short) 40, null)), 200);

            Book[] books = {book2, book1, book3}; // shuffled order

            List<Book> result = sortedByPageNum(books);

            assertEquals(List.of(book1, book3, book2),result);
        }

        @Test
        void shouldSortBooksByTitle() {
            Book book1 = new Book("Alpha", List.of(new Author("Alice", (short) 45, null)), 150);
            Book book2 = new Book("Gamma", List.of(new Author("Bob", (short) 50, null)), 300);
            Book book3 = new Book("Beta", List.of(new Author("David", (short) 40, null)), 200);

            Book[] books = {book2, book1, book3}; // shuffled order

            List<Book> result = sortedByPageName(books);

            assertEquals(List.of(book1, book3, book2),result);
        }

        @Test
        void shouldReturnAllAuthorsFromBooks() {
            Author author1 = new Author("Alice", (short) 45, null);
            Author author2 = new Author("Bob", (short) 50, null);
            Author author3 = new Author("Charlie", (short) 35, null);

            Book book1 = new Book("Book One", List.of(author1, author2), 150);
            Book book2 = new Book("Book Two", List.of(author3), 200);
            Book book3 = new Book("Book Three", List.of(author1), 300);

            Book[] books = {book1, book2, book3};

            var result = getAllAuthors(books);

            assertEquals(Set.of(author1, author2, author3), result);
        }

        @Test
        void testBookWithMaxPagesForAllAuthors() {
            // Create authors
            Author author1 = new Author("Author One", (short) 45, new ArrayList<>()); // No books
            Author author2 = new Author("Author Two", (short) 50, new ArrayList<>());
            Author author3 = new Author("Author Three", (short) 40, new ArrayList<>());

            // Create books
            Book book1 = new Book("Book A", List.of(author2), 300);
            Book book2 = new Book("Book B", List.of(author2), 150);
            Book book3 = new Book("Book C", List.of(author3), 300); // Same as book1

            // Assign books to authors
            author2.getBooks().addAll(List.of(book1, book2));
            author3.getBooks().add(book3);

            // Books array
            Author[] authors = {author1,author2,author3};

            // Expected result
            Map<Author, Optional<String>> expected = Map.of(
                    author1, Optional.empty(),  // No books
                    author2, Optional.of("Book A"), // Max pages = Book A (300)
                    author3, Optional.of("Book C")  // Only one book, so it must be returned
            );

            // Execute method
            Map<Author, Optional<String>> result = bookWithMaxPagesForAllAuthors(authors);

            // Assertion
            assertEquals(expected, result);
        }

        @Test
        void testBookTitleWithMaxPagesForAuthor() {
            // Arrange
            Author author1 = new Author("Author1", (short) 30, Arrays.asList(
                    new Book("Book A", null, 100),
                    new Book("Book B", null, 150),
                    new Book("Book C", null, 120)
            ));

            Author author2 = new Author("Author2", (short) 40, Arrays.asList(
                    new Book("Book D", null, 200),
                    new Book("Book E", null, 180)
            ));

            // Act
            Optional<String> resultForAuthor1 = bookTitleWithMaxPagesForAuthor(author1);
            Optional<String> resultForAuthor2 = bookTitleWithMaxPagesForAuthor(author2);

            // Assert
            assertTrue(resultForAuthor1.isPresent());
            assertEquals("Book B", resultForAuthor1.get());

            assertTrue(resultForAuthor2.isPresent());
            assertEquals("Book D", resultForAuthor2.get());
        }

        @Test
        void testBookTitleWithMaxPagesForAuthorNoBooks() {
            // Arrange
            Author authorWithoutBooks = new Author("No Books", (short) 50, Arrays.asList());

            // Act
            Optional<String> result = bookTitleWithMaxPagesForAuthor(authorWithoutBooks);

            // Assert
            assertFalse(result.isPresent());
        }
    }

}