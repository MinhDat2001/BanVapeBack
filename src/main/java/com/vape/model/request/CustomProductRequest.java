package com.vape.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomProductRequest {

    @JsonProperty("name")
    @SerializedName("name")
    private String name;

    @JsonProperty("avatar")
    @SerializedName("avatar")
    private String avatar;

    @JsonProperty("price")
    @SerializedName("price")
    private Integer price;

    @JsonProperty("quantity")
    @SerializedName("quantity")
    private Integer quantity;

    @JsonProperty("description")
    @SerializedName("description")
    private String description;

    @JsonProperty("category_ids")
    @SerializedName("category_ids")
    private List<Long> categoryIds;

    @JsonProperty("images")
    private List<String> images;

    public boolean isValid() {
        return name != null && !name.isEmpty()
                && description != null && !description.isEmpty()
                && price != null
                && categoryIds != null && !categoryIds.isEmpty()
                && quantity != null;
    }
}
