package com.vape.controller;

import com.vape.config.JwtTokenUtil;
import com.vape.entity.Account;
import com.vape.entity.User;
import com.vape.gmailAPI.GMailer;
import com.vape.gmailAPI.MailForm;
import com.vape.model.base.Error;
import com.vape.model.base.VapeResponse;
import com.vape.model.request.Authen;
import com.vape.model.request.Email;
import com.vape.model.request.ResetPassword;
import com.vape.model.request.UserRegisterRequest;
import com.vape.service.AccountService;
import com.vape.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

@RestController
@CrossOrigin
@RequestMapping("")
public class RegisterController {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    public HashMap<String, Integer> authenCode = new HashMap<>();
    public HashMap<String, Integer> forgotPasswordCode = new HashMap<>();

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public VapeResponse<User> Register(@RequestBody UserRegisterRequest request){
        try {
            Account accountExist = accountService.getAccountByEmail(request.getEmail());
            if (accountExist.getEmail().equals(request.getEmail())) {
                return VapeResponse.newInstance(Error.EXISTED.getErrorCode(), Error.EXISTED.getMessage(), null);
            }
        }catch (Exception e){
            // todo
        }
        try {
            Random generator = new Random();

            User user = new com.vape.entity.User();
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPhone(request.getPhone());
            user.setRole("USER");
            user.setStatus(0);
            user.setAddress(request.getAddress());
            userService.save(user);

            int code = generator.nextInt((9999 - 1000) + 1) + 1000;
            authenCode.put(user.getEmail(), code);

            new GMailer().sendMail(user.getEmail(), "Xác thực email", MailForm.mailForm(user.getName(), code));

            Account account = new Account();
            account.setEmail(request.getEmail());
            account.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
            accountService.save(account);
            System.out.println("code: "+ authenCode.get(user.getEmail()).toString());
            return VapeResponse.newInstance(Error.OK.getErrorCode(), Error.OK.getMessage(), user);
        }catch (Exception e){

            try {
                userService.deleteUserByEmail(request.getEmail());
            }catch (Exception ex){

            }

            System.out.println("Exception:" + e);
            return VapeResponse.newInstance(Error.NOT_OK.getErrorCode(), Error.NOT_OK.getMessage(), null);
        }
    }

    @RequestMapping(value = "/register/auth", method = RequestMethod.POST)
    public VapeResponse<User> authenRegister(@RequestBody Authen request){
        try{
            if(request.getCode() == authenCode.get(request.getEmail())) {
                User user = userService.getUserByEmail(request.getEmail());
                user.setStatus(1);
                authenCode.remove(request.getEmail());
                userService.save(user);
                return VapeResponse.newInstance(Error.OK.getErrorCode(), Error.OK.getMessage(), user);
            }
        }catch (Exception e){
            System.out.println("Exception: "+ e);
        }

        return VapeResponse.newInstance(Error.NOT_OK.getErrorCode(), Error.NOT_OK.getMessage(), null);
    }

    @RequestMapping(value = "/forgot-password", method = RequestMethod.POST)
    public VapeResponse<String> forgotPassword(@RequestBody Email request){
        try{
            Random generator = new Random();
            int code = generator.nextInt((9999 - 1000) + 1) + 1000;
            forgotPasswordCode.put(request.getEmail(), code);
            new GMailer().sendMail(request.getEmail(), "Xác thực email", MailForm.mailForm(userService.getUserByEmail(request.getEmail()).getName(), code));
            return VapeResponse.newInstance(Error.OK.getErrorCode(), Error.OK.getMessage(), null);
        }catch (Exception e){
            System.out.println("Exception: "+ e);
        }

        return VapeResponse.newInstance(Error.NOT_OK.getErrorCode(), Error.NOT_OK.getMessage(), null);
    }

    @RequestMapping(value = "/forgot-password/auth", method = RequestMethod.POST)
    public VapeResponse<String> forgotPasswordAuth(@RequestBody Authen request){
        try{
            System.out.println(request.getEmail());
            if(request.getCode() == forgotPasswordCode.get(request.getEmail())) {
                forgotPasswordCode.remove(request.getEmail());
                return VapeResponse.newInstance(Error.OK.getErrorCode(), Error.OK.getMessage(), null);
            }
        }catch (Exception e){
            System.out.println("Exception: "+ e);
        }

        return VapeResponse.newInstance(Error.NOT_OK.getErrorCode(), Error.NOT_OK.getMessage(), null);
    }

    @RequestMapping(value = "/reset-password", method = RequestMethod.POST)
    public VapeResponse<String> resetPassword(@RequestBody ResetPassword request){
        try{
            Account account = accountService.getAccountByEmail(request.getEmail());
            account.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
            accountService.save(account);
            return VapeResponse.newInstance(Error.OK.getErrorCode(), Error.OK.getMessage(), null);
        }catch (Exception e){
            System.out.println("Exception: "+ e);
        }

        return VapeResponse.newInstance(Error.NOT_OK.getErrorCode(), Error.NOT_OK.getMessage(), null);
    }
}
