package com.vape.model.base;

import com.google.gson.Gson;

public abstract class BaseRequest {

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
