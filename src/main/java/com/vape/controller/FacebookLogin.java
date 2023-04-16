package com.vape.controller;

import com.vape.config.JwtTokenUtil;
import com.vape.entity.Account;
import com.vape.entity.User;
import com.vape.facebook.FacebookUtils;
import com.vape.service.AccountService;
import com.vape.service.UserService;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class FacebookLogin {

    @Autowired
    private FacebookUtils restFb;
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService jwtInMemoryUserDetailsService;

    @RequestMapping("/login-facebook")
    public RedirectView loginFacebook(HttpServletRequest request, HttpServletResponse response) throws ClientProtocolException, IOException {
        String code = request.getParameter("code");

        if (code == null || code.isEmpty()) {
            return new RedirectView("/login");
        }

        String accessToken = restFb.getToken(code);

        com.vape.entity.User FBuser = restFb.getUserInfo(accessToken);

        User userExist = userService.getUserByEmail(FBuser.getEmail());
        if(userExist == null){
            userService.save(FBuser);
            Account account = new Account();
            account.setEmail(FBuser.getEmail());
            account.setPassword("password");
            accountService.save(account);
        }

        final UserDetails userDetails = jwtInMemoryUserDetailsService
                .loadUserByUsername(FBuser.getEmail());

        final String token = jwtTokenUtil.generateToken(userDetails);
        Cookie cookie = new Cookie("token", token);
        response.addCookie(cookie);

        return new RedirectView("http://localhost:3000/");
    }

}
