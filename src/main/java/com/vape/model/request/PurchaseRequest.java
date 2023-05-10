package com.vape.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseRequest {
    @JsonProperty("idCart")
    @SerializedName("idCart")
    private Long idCart;

    @JsonProperty("quantity")
    @SerializedName("quantity")
    private int quantity;

    @JsonProperty("totalPrice")
    @SerializedName("totalPrice")
    private int totalPrice;
}
