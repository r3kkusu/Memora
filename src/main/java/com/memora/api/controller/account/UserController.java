package com.memora.api.controller.account;

import com.memora.api.data.ResponseMessage;
import com.memora.api.data.dto.UserDto;
import com.memora.api.service.account.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/v1/account/register")
    public ResponseEntity<ResponseMessage<UserDto>> registerUser(@RequestBody UserDto userDto) {
        try {
            userService.registerUser(userDto);

            ResponseMessage<UserDto> responseMessage = new ResponseMessage<UserDto>();
            responseMessage.setMessage("Username " + userDto.getUsername() + " successfully registered");
            responseMessage.setData(userDto);

            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage<UserDto> responseMessage = new ResponseMessage<UserDto>(e.getMessage(), false, userDto);
            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
        }
    }
}
