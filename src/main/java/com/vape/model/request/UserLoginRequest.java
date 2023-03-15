package com.vape.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import com.vape.model.base.BaseRequest;

public class UserLoginRequest extends BaseRequest {
    @JsonProperty("email")
    @SerializedName("email")
    public String email;

    @JsonProperty("password")
    @SerializedName("password")
    public String password;
}
