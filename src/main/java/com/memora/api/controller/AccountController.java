package com.memora.api.controller;

import com.memora.api.data.ResponseMessage;
import com.memora.api.data.dto.LoginUserDto;
import com.memora.api.data.dto.RegisterUserDto;
import com.memora.api.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/account")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

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

        if (accountService.authenticateUser(loginUserDto)) {
            ResponseMessage<String> responseMessage = new ResponseMessage<>();
            responseMessage.setMessage("Login successful");
            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } else {
            ResponseMessage<String> responseMessage = new ResponseMessage<>();
            responseMessage.setMessage("Login failed. Invalid credentials.");
            return new ResponseEntity<>(responseMessage, HttpStatus.UNAUTHORIZED);
        }
    }
}
