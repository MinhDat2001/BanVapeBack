package com.vape.paypal;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import com.vape.config.JwtTokenUtil;
import com.vape.entity.Cart;
import com.vape.entity.Product;
import com.vape.model.CartStatus;
import com.vape.model.base.Error;
import com.vape.model.base.VapeResponse;
import com.vape.repository.ProductRepository;
import com.vape.service.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("")
@CrossOrigin
public class PaymentController {

    Map<Long, String> listCartId = new HashMap<>();
    public static final String URL_PAYPAL_SUCCESS = "paypal/success"; // todo: cấu hình url thanh toán thành công
    public static final String URL_PAYPAL_CANCEL = "paypal/cancel"; // todo: cấu hình url thanh toán thất bại
    private Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private PaypalService paypalService;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartService cartService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/payment")
    public VapeResponse<Object> pay(HttpServletRequest request, @RequestBody PaypalModel paypalModel) {
        String cancelUrl = Utils.getBaseURL(request) + "/" + URL_PAYPAL_CANCEL;
        String successUrl = Utils.getBaseURL(request) + "/" + URL_PAYPAL_SUCCESS;
        System.out.println("success: " + successUrl);
        System.out.println("cancel: " + cancelUrl);
        String requestTokenHeader = request.getHeader("token").substring(5);
        try {
            Payment payment = paypalService.createPayment(
                    paypalModel.getPrice(),
                    "USD",
                    PaypalPaymentMethod.paypal,
                    PaypalPaymentIntent.sale,
                    "Thanh toán đơn hàng Vape shop",
                    cancelUrl,
                    successUrl,
                    paypalModel.getCartId()
            );

            listCartId.put(paypalModel.getCartId(), requestTokenHeader);

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
    public RedirectView successPay(@RequestParam("paymentId") String paymentId, @RequestParam("token") String token, @RequestParam("PayerID") String payerId, @RequestParam("cartId") Long cartId, HttpServletResponse response) {
        String requestTokenHeader = listCartId.get(cartId);
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if (payment.getState().equalsIgnoreCase("approved")) {
                System.out.println();
                List<Cart> carts = cartService.getAll(jwtTokenUtil.getUsernameFromToken(requestTokenHeader));
                for (Cart cart1 : carts) {
                    Product product = productRepository.findById(cart1.getProductId()).orElseThrow(
                            () -> new RuntimeException("Không tìm thấy product có ID = " + cart1.getProductId())
                    );
                    product.setQuantity(product.getQuantity() - cart1.getQuantity());
                    productRepository.save(product);
                    cartService.deleteCart(cart1.getId());
                }
            }

            Cookie cookie = new Cookie("token", requestTokenHeader);
            response.addCookie(cookie);

            return new RedirectView("http://localhost:3000/cart");
        } catch (PayPalRESTException e) {
            log.error(e.getMessage());
        }

        Cookie cookie = new Cookie("token", requestTokenHeader);
        response.addCookie(cookie);

        return new RedirectView("http://localhost:3000/");
    }
}
