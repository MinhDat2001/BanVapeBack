package com.vape.model.comunication;

import net.minidev.json.JSONObject;

public class Response <T> {
    private int status;
    private String message;
    private JSONObject data;

    public Response() {
    }

    public Response(int status, String message, JSONObject data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }
}
