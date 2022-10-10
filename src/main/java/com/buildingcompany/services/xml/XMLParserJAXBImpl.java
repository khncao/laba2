package com.buildingcompany.services.xml;

import java.io.File;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.SAXException;

import com.buildingcompany.entities.Wrapper;
import com.buildingcompany.utility.ResourcePaths;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

public class XMLParserJAXBImpl implements IXMLParser {
    private Logger logger = LogManager.getLogger(XMLParserJAXBImpl.class);
    private SchemaFactory schemaFactory;

    public XMLParserJAXBImpl() {
        schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    }

    public <T> List<T> parse(String xmlFileNameNoExt, Class<T> entityClass) {
        try {
            JAXBContext context = JAXBContext.newInstance(Wrapper.class, entityClass);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            JAXBElement<Wrapper> result = unmarshaller
                    .unmarshal(new StreamSource(ResourcePaths.xmlPath + xmlFileNameNoExt + ".xml"), Wrapper.class);
            return result.getValue().getItems();
        } catch (JAXBException e) {
            logger.error(e.toString());
        }
        return null;
    }

    public <T> boolean validate(String xmlFileNameNoExt, String xsdFileNameNoExt, Class<T> entityClass) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(entityClass);
            Schema schema = schemaFactory.newSchema(new File(ResourcePaths.xsdPath + xsdFileNameNoExt + ".xsd"));
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            unmarshaller.setSchema(schema);
            StreamSource source = new StreamSource(new File(ResourcePaths.xmlPath + xmlFileNameNoExt + ".xml"));
            unmarshaller.unmarshal(source, entityClass);
            return true;
        } catch(JAXBException e) {
            logger.error(e.toString());
        } catch(SAXException e) {
            logger.error(e.toString());
        }
        return false;
    }
}
