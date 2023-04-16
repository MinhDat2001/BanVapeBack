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
    @JsonProperty("id_cart")
    @SerializedName("id_cart")
    private Long idCart;

    @JsonProperty("quantity")
    @SerializedName("quantity")
    private int quantity;

    @JsonProperty("total_price")
    @SerializedName("total_price")
    private int totalPrice;
}
