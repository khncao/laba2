package com.buildingcompany.services;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.buildingcompany.utility.Resources;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonJacksonImpl implements IJsonProcessor {
    private static final Logger logger = LogManager.getLogger(JsonJacksonImpl.class);

    public <T> List<T> parse(String fileNameNoExt, Class<T> entityClass) {
        ObjectMapper om = new ObjectMapper();
        try {
            return om.readValue(new File(Resources.jsonPath + fileNameNoExt + ".json"), new TypeReference<List<T>>(){});
        } catch (StreamReadException e) {
            logger.error(e);
        } catch (DatabindException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        }
        return null;
    }

    public <T> void write(String fileNameNoExt, List<T> instances) {
        ObjectMapper om = new ObjectMapper();
        try {
            om.writeValue(new File(Resources.jsonPath + fileNameNoExt + ".json"), instances);
        } catch (JsonProcessingException e) {
            logger.error(e);
        } catch (IOException e) {
            logger.error(e);
        }
    }
}
