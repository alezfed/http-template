package com.epam.izh.rd.online.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

@Component
public class SimpleObjectMapperFactory implements ObjectMapperFactory {
    @Override
    public ObjectMapper getObjectMapper() {
        return new ObjectMapper();
    }
}