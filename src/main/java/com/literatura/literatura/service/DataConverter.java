package com.literatura.literatura.service;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DataConverter implements IDataConverter{
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T obtainData(String json, Class<T> Tclass) {
        try {
            return objectMapper.readValue(json, Tclass);
        } catch (Exception e) {
            throw new RuntimeException("error converting data : " + e.getMessage());
        }
    }
}
