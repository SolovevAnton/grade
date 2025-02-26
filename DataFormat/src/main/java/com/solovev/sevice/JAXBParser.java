package com.solovev.sevice;

import com.solovev.model.Book;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class JAXBParser implements ParserXML {

    @Override
    public List<Book> parse(Path xml) {
        validateFile(xml);
        return getBooks(xml).bookList;
    }

    private void validateFile(Path file) {
        if (!Files.isRegularFile(file)) {
            throw new IllegalArgumentException("File is not a regular file [" + file + "]");
        }
    }


    private Books getBooks(Path xml) {
        JAXBContext context = null;
        try {
            context = JAXBContext.newInstance(Books.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            return (Books) unmarshaller.unmarshal(xml.toFile());
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }


    @XmlRootElement(name = "books")
    @XmlAccessorType(XmlAccessType.FIELD)
    private static class Books {


        @XmlElement(name = "book")
        private List<Book> bookList;

        public Books(List<Book> bookList) {
            this.bookList = bookList;
        }

        public Books() {
            bookList = null;
        }
    }
}
