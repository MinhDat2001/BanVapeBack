package com.vape.vnpay;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@RestController
public class PayController {
    final PayService payService;

    public PayController(PayService payService) {
        this.payService = payService;
    }

    @PostMapping("/pay")
    public String pay(@RequestBody PayModel payModel, HttpServletRequest request) {
        try {
            return payService.payWithVNPAY(payModel, request);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    // @RequestParam("") String

    @GetMapping("/vnpay/success")
    public RedirectView handleResult(@RequestParam(value = "vnp_Amount", required = false) Long vnp_Amount, @RequestParam(value = "vnp_OrderInfo", required = false) String vnp_OrderInfo,
                                     @RequestParam(value = "vnp_TmnCode", required = false) String vnp_TmnCode, @RequestParam("vnp_TxnRef") Long cartId,
                                     @RequestParam("vnp_BankCode") String vnp_BankCode, @RequestParam("vnp_BankTranNo") String vnp_BankTranNo,
                                     @RequestParam(value = "vnp_CardType", required = false) String vnp_CardType, @RequestParam(value = "vnp_PayDate", required = false) Long vnp_PayDate,
                                     @RequestParam("vnp_ResponseCode") String vnp_ResponseCode, @RequestParam(value = "vnp_TransactionNo", required = false) String vnp_TransactionNo,
                                     @RequestParam(value = "vnp_TransactionStatus", required = false) String vnp_TransactionStatus
    ) {
        if (vnp_ResponseCode != null && vnp_ResponseCode.equals("00")) {
            // todo: Duong handled update status cartId

            return new RedirectView("http://127.0.0.1:8088/vnpay/info?vnp_Amount=" + vnp_Amount + "&vnp_OrderInfo=" + vnp_OrderInfo + "&cartId=" + cartId + "&vnp_ResponseCode=" + vnp_ResponseCode + "&vnp_BankCode=" + vnp_BankCode + "&vnp_CardType=" + vnp_CardType);
        }
        return new RedirectView("http://127.0.0.1:8088/vnpay/fail");
    }
}
