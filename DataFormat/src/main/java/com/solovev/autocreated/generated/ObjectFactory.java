//
// This file was generated by the Eclipse Implementation of JAXB, v3.0.0 
// See https://eclipse-ee4j.github.io/jaxb-ri 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2025.02.26 at 05:47:39 PM MSK 
//


package com.solovev.autocreated.generated;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the generated package.
 * <p>An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Books }
     */
    public Books createBooks() {
        return new Books();
    }

    /**
     * Create an instance of {@link Books.Book }
     */
    public Books.Book createBooksBook() {
        return new Books.Book();
    }

    /**
     * Create an instance of {@link Books.Book.Publishers }
     */
    public Books.Book.Publishers createBooksBookPublishers() {
        return new Books.Book.Publishers();
    }

    /**
     * Create an instance of {@link Books.Book.Authors }
     */
    public Books.Book.Authors createBooksBookAuthors() {
        return new Books.Book.Authors();
    }

    /**
     * Create an instance of {@link Books.Book.Publishers.Publisher }
     */
    public Books.Book.Publishers.Publisher createBooksBookPublishersPublisher() {
        return new Books.Book.Publishers.Publisher();
    }

    /**
     * Create an instance of {@link Books.Book.Authors.Author }
     */
    public Books.Book.Authors.Author createBooksBookAuthorsAuthor() {
        return new Books.Book.Authors.Author();
    }

}
