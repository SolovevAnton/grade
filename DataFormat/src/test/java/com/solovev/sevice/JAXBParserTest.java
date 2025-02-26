package com.solovev.sevice;

public class JAXBParserTest extends ParserXMLBaseTest {
    @Override
    protected ParserXML provideParserXML() {
        return new JAXBParser();
    }
}
