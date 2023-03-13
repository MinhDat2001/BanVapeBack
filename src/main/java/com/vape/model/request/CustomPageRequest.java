package com.vape.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomPageRequest {

    @JsonProperty("page_number")
    @SerializedName("page_number")
    private Integer pageNumber;

    @JsonProperty("page_size")
    @SerializedName("page_size")
    private Integer pageSize;

    @JsonProperty("sort_field")
    @SerializedName("sort_field")
    private String sortField;

    @JsonProperty("sort_order")
    @SerializedName("sort_order")
    private String sortOrder;

    public boolean isValid() {
        return true;
    }

    public void checkData() {
        if (pageNumber == null) pageNumber = 1;
        if (pageSize == null) pageSize = 1;
        if (sortField == null) sortField = "id";
        if (sortOrder == null) sortOrder = "asc";
    }
}
