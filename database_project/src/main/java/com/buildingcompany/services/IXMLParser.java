package com.buildingcompany.services;

import java.util.List;

public interface IXMLParser {
    <T> List<T> parse(String fileNameNoExt, boolean shouldValidate);
    boolean validate(String fileNameNoExt);
}
