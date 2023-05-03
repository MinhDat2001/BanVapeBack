package com.vape.paypal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentSuccess {

    @JsonProperty("card_id")
    @SerializedName("card_id")
    private Long cartId;

    @JsonProperty("payment_id")
    @SerializedName("payment_id")
    private String paymentId;

    @JsonProperty("token")
    @SerializedName("token")
    private String token;

    @JsonProperty("payer_id")
    @SerializedName("payer_id")
    private String PayerID;
}
