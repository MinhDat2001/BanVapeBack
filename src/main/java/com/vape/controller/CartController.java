package com.vape.controller;

import com.vape.Constant.Constant;
import com.vape.config.JwtTokenUtil;
import com.vape.entity.Cart;
import com.vape.entity.Product;
import com.vape.entity.ProductDetail;
import com.vape.model.CartStatus;
import com.vape.model.base.Error;
import com.vape.model.base.VapeResponse;
import com.vape.model.reponse.ProductResponse;
import com.vape.model.request.CartRequest;
import com.vape.model.request.PurchaseRequest;
import com.vape.repository.CartRepository;
import com.vape.repository.ProductRepository;
import com.vape.service.CartService;
import com.vape.service.ProductDetailService;
import com.vape.service.ProductService;
import com.vape.view.CartView;
import com.vape.view.DetailView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductDetailService productDetailService;

    @GetMapping("/getAll")
    public VapeResponse<Object> getAllCart(HttpServletRequest request){
        String username = getUsernameFromUser(request);
        List<Cart> carts = cartService.getAll(username);
        return VapeResponse.newInstance(Error.OK, carts);
    }

    @PostMapping("/add")
    public VapeResponse<Object> createCart(@RequestBody CartRequest cartRequest,HttpServletRequest request){
        try {
            if (cartRequest == null) {
                return VapeResponse.newInstance(Error.NOT_OK, null);
            }
            String requestTokenHeader = request.getHeader("token").substring(5);
            Cart cart = new Cart();
            cart.setStatus(CartStatus.ACTIVE);
            cart.setEmail(jwtTokenUtil.getUsernameFromToken(requestTokenHeader));
            cart.setQuantity(cartRequest.getQuantity());
            ProductResponse product = productService.getProductById(cartRequest.getProductId());
            cart.setPrice(product.getPrice());
            cart.setProductId(product.getId());
            cart.setProductImg(product.getAvatar());
            cart.setProductName(product.getName());
            cartRepository.save(cart);

            return VapeResponse.newInstance(Error.OK, cart);
        }catch (Exception e){
            System.out.println("exception: "+e);
            return VapeResponse.newInstance(Error.OK.getErrorCode(), "Thêm giỏ hàng thất bại", null);
        }
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
    @PostMapping("/checkBuy")
    public VapeResponse<Object> checkBuyProduct(@RequestBody PurchaseRequest checkRequest, HttpServletRequest request){
//      check quantity
        String requestTokenHeader = request.getHeader("token").substring(5);
        List<Cart> carts = cartService.getAll(requestTokenHeader);
        Cart cart = new Cart();
        for(Cart cart1:carts){
            if(cart1.getId()==checkRequest.getIdCart()){
                cart=cart1;
            }
        }
        ProductResponse productResponse = productService.getProductById(cart.getProductId());
        if(productResponse.getQuantity()>checkRequest.getQuantity()){
            return VapeResponse.newInstance(Error.NOT_OK, null);
        }
        return VapeResponse.newInstance(Error.OK, null);
    }

    @PostMapping("/buy")
    public VapeResponse<Object> buyProduct(@RequestBody PurchaseRequest buyRequest, HttpServletRequest request){
        String requestTokenHeader = request.getHeader("token").substring(5);
        List<Cart> carts = cartService.getAll(requestTokenHeader);
        Cart cart = new Cart();
        for(Cart cart1:carts){
            if(cart1.getId()==buyRequest.getIdCart()){
                cart=cart1;
            }
        }
        Cart finalCart = cart;
        Product product = productRepository.findById(cart.getProductId()).orElseThrow(
                () -> new RuntimeException("Không tìm thấy product có ID = " + finalCart.getProductId())
        );
        product.setQuantity(product.getQuantity()-buyRequest.getQuantity());
        cartService.deleteCart(finalCart.getId());
        return VapeResponse.newInstance(Error.OK, null);
    }

    private String getUsernameFromUser(HttpServletRequest request){
        String requestTokenHeader = request.getHeader("token");
        String jwtToken = requestTokenHeader.substring(5);
        return jwtTokenUtil.getUsernameFromToken(jwtToken);
    }
}
