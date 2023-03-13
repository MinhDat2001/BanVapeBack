package com.vape.model.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class VapeResponse<T> {

    @JsonProperty("status")
    @SerializedName("status")
    private int status;

    @JsonProperty("message")
    @SerializedName("message")
    private String message;

    @JsonProperty("data")
    @SerializedName("data")
    private T data;

    public static <T> VapeResponse<T> newInstance(int status, String message, T data) {
        return VapeResponse.<T>builder()
                .status(status)
                .message(message)
                .data(data)
                .build();
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
