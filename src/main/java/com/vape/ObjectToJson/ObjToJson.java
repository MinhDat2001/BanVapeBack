package com.vape.ObjectToJson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.vape.entity.Product;
import org.springframework.context.annotation.Bean;

import java.util.List;


public class ObjToJson<T> {

    public ObjToJson(Object object){
    }

    public String convert(List<T> object) throws JsonProcessingException {
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return objectWriter.writeValueAsString(object);
    }
}
