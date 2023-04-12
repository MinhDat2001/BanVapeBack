package com.vape.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import com.vape.entity.ProductDetail;
import com.vape.entity.User;
import com.vape.model.CartStatus;
import com.vape.model.base.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartRequest extends BaseRequest {
    @JsonProperty("id")
    @SerializedName("id")
    private Long id;

    @JsonProperty("email")
    @SerializedName("email")
    private String email;

    @JsonProperty("quantity")
    @SerializedName("quantity")
    private int quantity;

    @JsonProperty("status")
    @SerializedName("status")
    private CartStatus status;
}
