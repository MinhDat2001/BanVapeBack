package com.vape.paypal;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.vape.entity.Cart;
import com.vape.model.CartStatus;
import com.vape.model.base.Error;
import com.vape.model.base.VapeResponse;
import com.vape.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("")
public class PaymentController {
    public static final String URL_PAYPAL_SUCCESS = "paypal/success"; // todo: cấu hình url thanh toán thành công
    public static final String URL_PAYPAL_CANCEL = "paypal/cancel"; // todo: cấu hình url thanh toán thất bại
    private Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private PaypalService paypalService;

    @Autowired
    private CartService cartService;

    @PostMapping("/payment")
    public VapeResponse<Object> pay(HttpServletRequest request, @RequestParam("price") double price, @RequestParam("cartId") Long cartId) {
        String cancelUrl = Utils.getBaseURL(request) + "/" + URL_PAYPAL_CANCEL;
        String successUrl = Utils.getBaseURL(request) + "/" + URL_PAYPAL_SUCCESS;
        System.out.println("success: " + successUrl);
        System.out.println("cancel: " + cancelUrl);
        try {
            Payment payment = paypalService.createPayment(
                    price,
                    "USD",
                    PaypalPaymentMethod.paypal,
                    PaypalPaymentIntent.sale,
                    "Thanh toán đơn hàng Vape shop",
                    cancelUrl,
                    successUrl,
                    cartId
            );
            for (Links links : payment.getLinks()) {
                if (links.getRel().equals("approval_url")) {
                    return VapeResponse.newInstance(Error.OK, links.getHref());
                }
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return VapeResponse.newInstance(Error.NOT_OK, "Có lỗi khi thanh toán");
    }

    @GetMapping(URL_PAYPAL_CANCEL)
    public RedirectView cancelPay() {
        return new RedirectView("http://127.0.0.1:8088/paypal/fail");
    }

    @GetMapping(URL_PAYPAL_SUCCESS)
    public RedirectView successPay(@RequestParam("paymentId") String paymentId, @RequestParam("token") String token, @RequestParam("PayerID") String payerId, @RequestParam("cartId") Long cartId,HttpServletRequest request) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equalsIgnoreCase("approved")) {
                System.out.println();
            }
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }
        return new RedirectView("http://127.0.0.1:8088/paypal/fail");
    }
}
