package com.vape.helper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import net.minidev.json.JSONObject;

import java.util.Map;

public class ConvertJson {
    public static JSONObject ObjectToJsonObject(Object object) throws JsonProcessingException {
        JSONObject json = new JSONObject();
        Map<String, String> map = (Map<String, String>) object;

        return  json;
    }
}
