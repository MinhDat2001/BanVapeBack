package com.vape.controller;

import com.vape.entity.Account;
import com.vape.entity.User;
import com.vape.model.reponse.ProductResponse;
import com.vape.model.request.CustomPageRequest;
import com.vape.model.request.UserIdentifyRequest;
import com.vape.model.request.UserRegisterRequest;
import com.vape.model.base.Error;
import com.vape.model.base.VapeResponse;
import com.vape.service.AccountService;
import com.vape.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public VapeResponse<User> createUser(@RequestBody UserRegisterRequest request){

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

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public VapeResponse<User> updateUser(@RequestBody UserRegisterRequest request){
        try{
            User user = userService.getUserByEmail(request.getEmail());
            user.setAddress(request.getAddress());
            user.setName(request.getName());
            user.setPhone(request.getPhone());
            userService.save(user);
            return VapeResponse.newInstance(Error.OK.getErrorCode(), Error.OK.getMessage(), user);
        }catch (Exception e){
            return VapeResponse.newInstance(Error.NOT_OK.getErrorCode(), Error.NOT_OK.getMessage(), null);
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public VapeResponse<User> deleteUser(@RequestBody UserIdentifyRequest request){
        try{
            User user = userService.getUserByEmail(request.getEmail());
            userService.delete(user);
            return VapeResponse.newInstance(Error.OK.getErrorCode(), Error.OK.getMessage(), null);
        }catch (Exception e){
            return VapeResponse.newInstance(Error.NOT_OK.getErrorCode(), Error.NOT_OK.getMessage(), null);
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public VapeResponse<User> getUserByEmail(@RequestBody UserIdentifyRequest request){
        try{
            User user = userService.getUserByEmail(request.getEmail());
            return VapeResponse.newInstance(Error.OK.getErrorCode(), Error.OK.getMessage(), user);
        }catch (Exception e){
            return VapeResponse.newInstance(Error.NOT_OK.getErrorCode(), Error.NOT_OK.getMessage(), null);
        }
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public VapeResponse<Page<User>> getAllUser(@RequestBody CustomPageRequest request){
        try{
            request.checkData();
            Page<User> users = (Page<User>) userService.getAllUsers(request.getPageNumber(),
                    request.getPageSize(),
                    request.getSortField(),
                    request.getSortOrder());
            return VapeResponse.newInstance(Error.OK.getErrorCode(), Error.OK.getMessage(), users);
        }catch (Exception e){
            return VapeResponse.newInstance(Error.NOT_OK.getErrorCode(), Error.NOT_OK.getMessage(), null);
        }
    }
}