package com.vape.vnpay;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PayModel {

    public Long vnp_Ammount;

    public String vnp_OrderInfo;

    public String vnp_OrderType = "200000";

    public Long vnp_TxnRef;
}
