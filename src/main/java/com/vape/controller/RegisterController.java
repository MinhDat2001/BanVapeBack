package com.vape.controller;

import com.vape.entity.Account;
import com.vape.entity.User;
import com.vape.model.base.Error;
import com.vape.model.base.VapeResponse;
import com.vape.model.request.UserRegisterRequest;
import com.vape.service.AccountService;
import com.vape.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin()
@RequestMapping("")
public class RegisterController {
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
            User user = new com.vape.entity.User();
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPhone(request.getPhone());
            user.setRole("USER");
            user.setAddress(request.getAddress());
            userService.save(user);

            Account account = new Account();
            account.setEmail(request.getEmail());
            account.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
            accountService.save(account);
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
}
