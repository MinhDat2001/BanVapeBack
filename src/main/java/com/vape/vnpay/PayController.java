package com.vape.vnpay;

import com.vape.config.JwtTokenUtil;
import com.vape.entity.Cart;
import com.vape.entity.Product;
import com.vape.repository.ProductRepository;
import com.vape.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class PayController {

    Map<Long, String> listTxnRef = new HashMap<>();

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CartService cartService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    final PayService payService;

    public PayController(PayService payService) {
        this.payService = payService;
    }

    @PostMapping("/pay")
    public String pay(@RequestBody PayModel payModel, HttpServletRequest request) {
        String requestTokenHeader = request.getHeader("token").substring(5);

        try {
            System.out.println(payModel.getVnp_TxnRef()+"--------"+requestTokenHeader);
            listTxnRef.put(payModel.getVnp_TxnRef(), requestTokenHeader);
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
                                     @RequestParam(value = "vnp_TransactionStatus", required = false) String vnp_TransactionStatus,
                                     HttpServletRequest request,
             HttpServletResponse response
    ) {
        String requestTokenHeader = listTxnRef.get(cartId);
        try {

            if (vnp_ResponseCode != null && vnp_ResponseCode.equals("00")) {
                List<Cart> carts = cartService.getAll(jwtTokenUtil.getUsernameFromToken(requestTokenHeader));
                for (Cart cart1 : carts) {
                    Product product = productRepository.findById(cart1.getProductId()).orElseThrow(
                            () -> new RuntimeException("Không tìm thấy product có ID = " + cart1.getProductId())
                    );
                    product.setQuantity(product.getQuantity() - cart1.getQuantity());
                    productRepository.save(product);
                    cartService.deleteCart(cart1.getId());
                }

                Cookie cookie = new Cookie("token", requestTokenHeader);
                response.addCookie(cookie);

                return new RedirectView("http://localhost:3000/cart");
            }
        }catch (Exception e){
            System.out.println("exception: "+e);
        }

        Cookie cookie = new Cookie("token", requestTokenHeader);
        response.addCookie(cookie);
        return new RedirectView("http://127.0.0.1:3000/");
    }
}
