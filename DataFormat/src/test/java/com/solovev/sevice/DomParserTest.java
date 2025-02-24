package com.solovev.sevice;

class DomParserTest extends ParserXMLBaseTest {
    @Override
    protected ParserXML provideParserXML() {
        return new DomParser();
    }
}