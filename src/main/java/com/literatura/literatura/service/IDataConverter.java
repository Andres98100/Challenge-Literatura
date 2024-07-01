package com.literatura.literatura.service;

public interface IDataConverter {
    <T> T obtainData(String data, Class<T> type);
}
