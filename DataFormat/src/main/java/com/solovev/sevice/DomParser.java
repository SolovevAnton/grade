package com.solovev.sevice;

import com.solovev.model.Author;
import com.solovev.model.Book;
import com.solovev.model.Publisher;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DomParser implements ParserXML {

    @Override
    @SneakyThrows
    public List<Book> parse(@NonNull Path xml) {
        Document doc = getDoc(xml);
        var books = doc.getDocumentElement().getChildNodes();
        return toElements(books)
                .map(this::createBook)
                .toList();
    }

    private Document getDoc(Path xml) throws ParserConfigurationException, IOException, SAXException {
        validateFile(xml);
        return DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(xml.toFile());
    }

    private void validateFile(Path file) {
        if (!Files.isRegularFile(file)) {
            throw new IllegalArgumentException("File is not a regular file [" + file + "]");
        }
    }


    private Stream<Element> toElements(NodeList nodes) {
        return IntStream
                .range(0, nodes.getLength())
                .mapToObj(nodes::item)
                .filter(n -> n.getNodeType() == Node.ELEMENT_NODE)
                .map(n -> (Element) n);
    }

    private Book createBook(Element bookElement) {
        String title = bookElement.getElementsByTagName("name").item(0).getTextContent();
        LocalDate date = LocalDate.parse(bookElement.getElementsByTagName("publishDate").item(0).getTextContent());
        List<Author> authors = extract("author", bookElement).map(this::createAuthor).toList();
        List<Publisher> publishers = extract("publisher", bookElement).map(this::createPublisher).toList();
        return new Book(title, date, authors, publishers);
    }

    private Stream<Element> extract(String nodeName, Element bookElement) {
        return toElements(bookElement.getElementsByTagName(nodeName));
    }

    private Author createAuthor(Element authorElement) {
        String authorName = authorElement.getElementsByTagName("name").item(0).getTextContent();
        String authorSurName = authorElement.getElementsByTagName("surname").item(0).getTextContent();
        return new Author(authorName, authorSurName);
    }

    private Publisher createPublisher(Element publisherElement) {
        String publisherName = publisherElement.getElementsByTagName("name").item(0).getTextContent();
        String email = publisherElement.getElementsByTagName("email").item(0).getTextContent();
        return new Publisher(publisherName, email);
    }


}
