package com.vape.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import com.vape.model.base.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRequest extends BaseRequest {

    @JsonProperty("id")
    @SerializedName("id")
    private Long id;

    @JsonProperty("name")
    @SerializedName("name")
    private String name;

    @JsonProperty("description")
    @SerializedName("description")
    private String description;

    public boolean isValid() {
        return name != null && !name.isEmpty() && description != null && !description.isEmpty();
    }
}
