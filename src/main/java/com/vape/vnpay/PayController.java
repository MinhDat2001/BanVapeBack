package com.vape.vnpay;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@RestController
public class PayController {
    final PayService payService;

    public PayController(PayService payService) {
        this.payService = payService;
    }

    @PostMapping("/pay")
    public String pay(@RequestBody PayModel payModel, HttpServletRequest request){
        try {
            return payService.payWithVNPAY(payModel, request);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }
}
