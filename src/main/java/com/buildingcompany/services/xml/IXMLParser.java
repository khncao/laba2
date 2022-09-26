package com.buildingcompany.services.xml;

import java.util.List;

public interface IXMLParser {
    <T> List<T> parse(String xmlFileNameNoExt, Class<T> entityClass);
    <T> boolean validate(String xmlFileNameNoExt, String xsdFileNameNoExt, Class<T> entityClass);
}
