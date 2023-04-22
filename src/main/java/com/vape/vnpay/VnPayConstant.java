package com.vape.vnpay;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public abstract class VnPayConstant {
    public static String vnp_Version = "2.1.0";
    public static String vnp_Command = "pay";

    // todo: custom mã website gửi về sau khi đăng kí trên sandbox
    public static String vnp_TmnCode = "BJ9ZILWP";
    public static String vnp_HashSecret = "HEMVEOBMAWSXSMKKUTEDHPFWEPXSTVRY";
    public static String vnp_Url = "https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";
    public static String vnp_BankCode = "NCB";
    public static String vnp_CurrCode = "VND";
    public static String vnp_Locale = "vn";

    // todo: custom url nó sẽ truyền lại các tham số là thông tin đơn hàng vừa nãy và trạng thái thanh toán
    public static String vnp_ReturnUrl = "localhost:8088/success";
}
