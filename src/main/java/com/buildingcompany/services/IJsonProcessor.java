package com.buildingcompany.services;

import java.util.List;

public interface IJsonProcessor {
    <T> List<T> parse(String fileNameNoExt, Class<T> entityClass);
    <T> void write(String fileNameNoExt, List<T> instances);
}
