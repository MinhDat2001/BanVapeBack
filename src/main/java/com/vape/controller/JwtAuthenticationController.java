package com.vape.controller;


import java.util.Objects;

import com.vape.entity.Account;
import com.vape.model.base.Error;
import com.vape.model.base.VapeResponse;
import com.vape.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.vape.config.JwtTokenUtil;
import com.vape.model.request.JwtRequest;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService jwtInMemoryUserDetailsService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(value = "/admin/login", method = RequestMethod.POST)
    public VapeResponse<String> adminLogin(@RequestBody JwtRequest authenticationRequest){
        try {
            Account account = accountService.authenAdmin(authenticationRequest.getUsername());

            if(account.getPassword().equals(bCryptPasswordEncoder.encode(authenticationRequest.getPassword()))){

                final UserDetails userDetails = jwtInMemoryUserDetailsService
                        .loadUserByUsername(authenticationRequest.getUsername());

                String token = jwtTokenUtil.generateToken(userDetails);
                return VapeResponse.newInstance(Error.OK.getErrorCode(), Error.OK.getMessage(), token);
            }
        }catch (Exception e){
            System.out.println("Exception:" +e);
        }
        return VapeResponse.newInstance(Error.INVALID_AUTHENTICATION.getErrorCode(), Error.INVALID_AUTHENTICATION.getMessage(), null);
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public VapeResponse<String> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest){
        try {
            authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

            final UserDetails userDetails = jwtInMemoryUserDetailsService
                    .loadUserByUsername(authenticationRequest.getUsername());

            final String token = jwtTokenUtil.generateToken(userDetails);
            return VapeResponse.newInstance(Error.OK.getErrorCode(), Error.OK.getMessage(), token);
        }catch (Exception e){
            return VapeResponse.newInstance(Error.INVALID_AUTHENTICATION.getErrorCode(), Error.INVALID_AUTHENTICATION.getMessage(), null);
        }
    }

    private void authenticate(String username, String password) throws Exception {
        Objects.requireNonNull(username);
        Objects.requireNonNull(password);

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            System.out.println("USER_DISABLED");
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            System.out.println("INVALID_CREDENTIALS");
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
