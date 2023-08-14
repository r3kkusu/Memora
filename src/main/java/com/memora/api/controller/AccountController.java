package com.memora.api.controller;

import com.memora.api.data.ResponseMessage;
import com.memora.api.data.dto.LoginUserDto;
import com.memora.api.data.dto.RegisterUserDto;
import com.memora.api.service.account.AccountService;
import com.memora.api.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;


    @PostMapping("/register")
    public ResponseEntity<ResponseMessage<RegisterUserDto>> registerUser(@RequestBody RegisterUserDto registerUserDto) {
        try {
            accountService.registerUser(registerUserDto);

            ResponseMessage<RegisterUserDto> responseMessage = new ResponseMessage<RegisterUserDto>();
            responseMessage.setMessage("Username " + registerUserDto.getUsername() + " successfully registered");
            responseMessage.setData(registerUserDto);

            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage<RegisterUserDto> responseMessage = new ResponseMessage<RegisterUserDto>(e.getMessage(), false, registerUserDto);
            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseMessage<String>> login(@RequestBody LoginUserDto loginUserDto) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginUserDto.getEmail(), loginUserDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String jwtToken = jwtUtils.generateJwtToken(userDetails.getUsername());


//        if (accountService.authenticateUser(loginUserDto)) {
            ResponseMessage<String> responseMessage = new ResponseMessage<>();
            responseMessage.setMessage("Login successful");
            responseMessage.setData(jwtToken);
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
//        } else {
//            ResponseMessage<String> responseMessage = new ResponseMessage<>();
//            responseMessage.setMessage("Login failed. Invalid credentials.");
//            return new ResponseEntity<>(responseMessage, HttpStatus.UNAUTHORIZED);
//        }
    }


}
