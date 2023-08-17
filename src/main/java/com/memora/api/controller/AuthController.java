package com.memora.api.controller;

import com.memora.api.data.ResponseMessage;
import com.memora.api.data.dto.SignInUserDto;
import com.memora.api.data.dto.SignUpUserDto;
import com.memora.api.service.AuthService;
import com.memora.api.service.JwtService;
import com.memora.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/auth")
public class AuthController {

    private final UserService userService;
    private final AuthService authService;
    private final JwtService jwtService;

    @PostMapping("/signUp")
    public ResponseEntity<ResponseMessage<SignUpUserDto>> signUp(@RequestBody SignUpUserDto signUpUserDto) {
        try {
            userService.signUp(signUpUserDto);

            ResponseMessage<SignUpUserDto> responseMessage = new ResponseMessage<SignUpUserDto>();
            responseMessage.setMessage("Username " + signUpUserDto.getUsername() + " successfully registered");
            responseMessage.setData(signUpUserDto);

            return new ResponseEntity<>(responseMessage, HttpStatus.OK);
        } catch (Exception e) {
            ResponseMessage<SignUpUserDto> responseMessage = new ResponseMessage<SignUpUserDto>(e.getMessage(), false, signUpUserDto);
            return new ResponseEntity<>(responseMessage, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/signIn")
    public ResponseEntity<ResponseMessage<String>> signIn(@RequestBody SignInUserDto signInUserDto) {
        ResponseMessage<String> responseMessage = new ResponseMessage<>();

        if (authService.authenticate(signInUserDto)) {
            String jwtToken = jwtService.generateToken(signInUserDto.getEmail());
            responseMessage.setMessage("Login successful");
            responseMessage.setData(jwtToken);
            return ResponseEntity.ok(responseMessage);
        } else {
            responseMessage.setMessage("Login failed. Invalid credentials.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseMessage);
        }
    }
}
