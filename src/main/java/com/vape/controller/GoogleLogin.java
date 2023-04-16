package com.vape.controller;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.vape.config.JwtTokenUtil;
import com.vape.entity.Account;
import com.vape.entity.User;
import com.vape.google.GooglePojo;
import com.vape.google.GoogleUtils;
import com.vape.model.base.Error;
import com.vape.model.base.VapeResponse;
import com.vape.service.AccountService;
import com.vape.service.UserService;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class GoogleLogin {

    @Autowired
    private GoogleUtils googleUtils;
    @Autowired
    private UserService userService;
    @Autowired
    private AccountService accountService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService jwtInMemoryUserDetailsService;

    @GetMapping("/login-google")
    public RedirectView loginGoogle(HttpServletRequest request, HttpServletResponse response) throws ClientProtocolException, IOException {

        String code = request.getParameter("code");

        if (code == null || code.isEmpty()) {
            return new RedirectView("/");
        }

        String accessToken = googleUtils.getToken(code);

        GooglePojo googlePojo = googleUtils.getUserInfo(accessToken);
        User userExist = userService.getUserByEmail(googlePojo.getEmail());
        System.out.println(userExist);
        if(userExist == null){
            User user = new User();
            user.setEmail(googlePojo.getEmail());
            user.setAvatar(googlePojo.getPicture());
            user.setStatus(1);
            userService.save(user);
            Account account = new Account();
            account.setEmail(googlePojo.getEmail());
            account.setPassword("password");
            accountService.save(account);
        }

        final UserDetails userDetails = jwtInMemoryUserDetailsService
                .loadUserByUsername(googlePojo.getEmail());

        final String token = jwtTokenUtil.generateToken(userDetails);
        Cookie cookie = new Cookie("token", token);
        response.addCookie(cookie);

        return new RedirectView("http://localhost:3000/");
    }
}
