package com.buildingcompany.services;

import java.util.List;

public interface IXMLParser {
    <T> List<T> parse(String xmlFileNameNoExt, Class<T> entityClass);
    boolean validate(String xmlFileNameNoExt, String xsdFileNameNoExt);
}
