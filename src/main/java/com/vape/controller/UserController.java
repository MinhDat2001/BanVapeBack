package com.vape.controller;

import com.vape.entity.Account;
import com.vape.helper.ConvertJson;
import com.vape.model.User;
import com.vape.model.comunication.Response;
import com.vape.service.AccountService;
import com.vape.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin()
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @RequestMapping("/new")
    public Response createUser(@RequestBody User request){

        try {
            Account accountExist = accountService.getAccountByEmail(request.getEmail());
            if (accountExist.getEmail().equals(request.getEmail())) {
                return new Response(400, "Email exist!", null);
            }
        }catch (Exception e){
            // todo
        }
        try {
            com.vape.entity.User user = new com.vape.entity.User();
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPhone(request.getPhone());
            user.setRole("USER");
            userService.save(user);

            Account account = new Account();
            account.setEmail(request.getEmail());
            account.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
            accountService.save(account);
            return new Response(200, "Success", ConvertJson.ObjectToJsonObject(user));
        }catch (Exception e){

            try {
                userService.deleteUserByEmail(request.getEmail());
            }catch (Exception ex){
            }

            System.out.println("Exception:" + e);
            return new Response(400, "False", null);
        }
    }
}
