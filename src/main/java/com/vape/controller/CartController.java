package com.vape.controller;

import com.vape.Constant.Constant;
import com.vape.config.JwtTokenUtil;
import com.vape.entity.Cart;
import com.vape.entity.Product;
import com.vape.entity.ProductDetail;
import com.vape.model.CartStatus;
import com.vape.model.base.Error;
import com.vape.model.base.VapeResponse;
import com.vape.model.request.CartRequest;
import com.vape.model.request.PurchaseRequest;
import com.vape.service.CartService;
import com.vape.service.ProductDetailService;
import com.vape.view.CartView;
import com.vape.view.DetailView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CartService cartService;
    @Autowired
    private ProductDetailService productDetailService;

    @GetMapping("/getAll")
    public VapeResponse<Object> getAllCart(HttpServletRequest request){
        String username = getUsernameFromUser(request);
        List<CartView> carts = cartService.getAll(username).stream().map(cart -> CartView.builder()
                .id(cart.getId())
                .email(cart.getEmail())
                .status(cart.getStatus())
                .quantity(cart.getQuantity())
                .detailView(DetailView.builder()
                        .productID(cart.getProductDetail().getProduct().getId())
                        .id(cart.getProductDetail().getId())
                        .color(cart.getProductDetail().getColor())
                        .avatar(cart.getProductDetail().getAvatar())
                        .quantity(cart.getProductDetail().getQuantity())
                        .build())
                .build()).collect(Collectors.toList());
        System.out.println(VapeResponse.newInstance(Error.OK, carts));
        return VapeResponse.newInstance(Error.OK, carts);
    }

    @PostMapping
    public VapeResponse<Object> createCart(@RequestBody CartRequest cartRequest){
        if (cartRequest == null){
            return VapeResponse.newInstance(Error.NOT_OK, null);
        }
        boolean isCreated = cartService.createCart(cartRequest) != null;
        return isCreated
                ? VapeResponse.newInstance(Error.OK, true)
                : VapeResponse.newInstance(Error.NOT_OK, false);
    }

    @PutMapping
    public VapeResponse<Object> updateCart(@RequestBody CartRequest cartRequest){
        boolean isExist = cartService.updateCart(cartRequest) != null;
        return isExist ? VapeResponse.newInstance(Error.OK, true) : VapeResponse.newInstance(Error.NOT_OK, false);
    }

    @DeleteMapping("/delete/{id}")
    public VapeResponse<Object> deleteCart(@PathVariable Long id){
        boolean deleteCart = cartService.deleteCart(id);
        return deleteCart ? VapeResponse.newInstance(Error.OK, true) : VapeResponse.newInstance(Error.NOT_OK, false);
    }

    //mua hang
    @PostMapping("/buy")
    public VapeResponse<Object> buyProduct(@RequestBody PurchaseRequest purchaseRequest){
        Optional<Cart> cart = cartService.getOne(purchaseRequest.getIdCart());
        if (cart.isPresent()){
            ProductDetail productDetail = cart.get().getProductDetail();
            Product product = productDetail.getProduct();
            int price = product.getPrice();
            //neu so luong trong cart lon hon so luong trong product detail
            if (cart.get().getQuantity() > productDetail.getQuantity()){
                return VapeResponse.newInstance(Error.INVALID_TOTAL_PRICE_OR_QUANTITY, null);
            }
            // neu don hang da duoc thanh toan
            if (cart.get().getStatus() == CartStatus.BOUGHT){
                return VapeResponse.newInstance(Error.IS_PURCHASED, cart.get());
            }else {
                //neu so tien truyen vao dung voi gia cua product
                if (purchaseRequest.getTotalPrice() == price * cart.get().getQuantity()) {
                    //cap nhat trang thai don hang
                    cart.get().setStatus(CartStatus.BOUGHT);
                    cartService.purchase(cart.get());

                    //cap nhat so luong trong product detail
                    productDetail.setQuantity(productDetail.getQuantity() - cart.get().getQuantity());
                    productDetailService.updateProductDetail(productDetail);
                    return VapeResponse.newInstance(Error.PURCHASE, cart);
                }
                return VapeResponse.newInstance(Error.INVALID_TOTAL_PRICE_OR_QUANTITY, null);
            }
        }
        return VapeResponse.newInstance(Error.NOT_EXISTED, null);
    }

    private String getUsernameFromUser(HttpServletRequest request){
        String requestTokenHeader = request.getHeader("token");
        String jwtToken = requestTokenHeader.substring(5);
        return jwtTokenUtil.getUsernameFromToken(jwtToken);
    }
}
