package com.vape.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailRequest {

    @JsonProperty("quantity")
    @SerializedName("quantity")
    private Integer quantity;

    @JsonProperty("avatar_detail")
    @SerializedName("avatar_detail")
    private String avatarDetail;

    @JsonProperty("color")
    @SerializedName("color")
    private String color;

    @JsonIgnore
    public boolean isValid() {
        return quantity != null && color != null && !color.isEmpty();
    }
}
