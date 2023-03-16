package com.vape.ObjectToJson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.google.gson.Gson;
import com.vape.entity.Product;
import net.minidev.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ObjToJson<T> {

    public ObjToJson(){
    }

    public JSONObject convert(List<T> object){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("object recieved", object);
        return jsonObject;
    }
}
