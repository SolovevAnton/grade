package com.solovev.model;


import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@XmlRootElement(name = "Book")
@XmlAccessorType(XmlAccessType.FIELD)
public class Book {

    @XmlElement(name = "name")
    private final String name;

    @XmlElement(name = "publishDate")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private final LocalDate publishDate;

    @XmlElementWrapper(name = "authors")
    @XmlElement(name = "author")
    private final List<Author> authors;

    @XmlElementWrapper(name = "publishers")
    @XmlElement(name = "publisher")
    private final List<Publisher> publishers;

    public Book() {
        name = null;
        publishDate = null;
        authors = null;
        publishers = null;
    }

    public Book(String name, LocalDate publishDate, List<Author> authors, List<Publisher> publishers) {
        this.name = name;
        this.publishDate = publishDate;
        this.authors = authors;
        this.publishers = publishers;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(name, book.name) && Objects.equals(publishDate,
                book.publishDate) && Objects.equals(authors, book.authors) && Objects.equals(publishers,
                book.publishers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, publishDate, authors, publishers);
    }

    public static class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

        private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

        @Override
        public LocalDate unmarshal(String v) {
            return LocalDate.parse(v, FORMATTER);
        }

        @Override
        public String marshal(LocalDate v) {
            return v.format(FORMATTER);
        }
    }
}
