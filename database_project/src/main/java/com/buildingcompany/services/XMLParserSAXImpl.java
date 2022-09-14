package com.buildingcompany.services;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.parsers.FactoryConfigurationError;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.SchemaFactoryConfigurationError;
import javax.xml.validation.Validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import com.buildingcompany.dao.sax.GenericCollectionHandler;

public class XMLParserSAXImpl implements IXMLParser {
    private static Logger logger = LogManager.getLogger(XMLParserSAXImpl.class);
    private final String relPath = "database_project/src/main/resources/xml/";
    private final String handlerModulePath = "com.buildingcompany.dao.sax.";
    private SAXParserFactory parserFactory;
    private SchemaFactory schemaFactory;
    private Schema schema;

    public XMLParserSAXImpl() {
        try {
            parserFactory = SAXParserFactory.newInstance();
            schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        } catch(FactoryConfigurationError e) {
            logger.error(e.toString());
        } catch(SchemaFactoryConfigurationError e) {
            logger.error(e.toString());
        }
    }

    public <T> List<T> parse(String fileNameNoExt, boolean shouldValidate) {
        try {
            Class c = Class.forName(handlerModulePath + fileNameNoExt + "Handler");
            GenericCollectionHandler<T> handler = (GenericCollectionHandler<T>)c.getDeclaredConstructor().newInstance();
            if(shouldValidate) {
                schema = schemaFactory.newSchema(new File(relPath + fileNameNoExt + ".xsd"));
                parserFactory.setSchema(schema);
            } else {
                parserFactory.setSchema(null);
            }
            SAXParser saxParser = parserFactory.newSAXParser();
            saxParser.parse(relPath + fileNameNoExt + ".xml", handler);
            return handler.getResults();
        } catch(ClassNotFoundException e) {
            logger.error(e.toString());
        } catch(IOException e) {
            logger.error(e.toString());
        } catch(ParserConfigurationException e) {
            logger.error(e.toString());
        } catch(SAXException e) {
            logger.error(e.toString());
        } catch(Exception e) {
            logger.error(e.toString() + '\n' + e.getStackTrace());
        }
        return null;
    }

    public boolean validate(String fileNameNoExt) {
        try {
            schema = schemaFactory.newSchema(new File(relPath + fileNameNoExt + ".xsd"));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(relPath + fileNameNoExt + ".xml")));
        } catch(SAXException e) {
            logger.error(e.toString());
            return false;
        } catch(IOException e) {
            logger.error(e.toString());
            return false;
        }
        return true;
    }
}
