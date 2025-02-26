package com.solovev.sevice;

import com.solovev.model.Author;
import com.solovev.model.Book;
import com.solovev.model.Publisher;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilderFactory;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DomParser implements ParserXML {

    @Override
    public List<Book> parse(Path xml) {
        Document doc = getDoc(xml);
        NodeList books = doc.getDocumentElement().getChildNodes();
        return toElements(books)
                .map(this::createBook)
                .collect(Collectors.toList());
    }

    private Document getDoc(Path xml) {
        validateFile(xml);
        try {
            return DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(xml.toFile());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
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
        List<Author> authors = extract("author", bookElement).map(this::createAuthor).collect(Collectors.toList());
        List<Publisher> publishers =
                extract("publisher", bookElement).map(this::createPublisher).collect(Collectors.toList());
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
