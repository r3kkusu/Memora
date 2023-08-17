package com.memora.api.service;

import com.memora.api.data.dto.SignInUserDto;
import com.memora.api.data.dto.SignUpUserDto;
import com.memora.api.data.model.User;

public interface AuthService {
    boolean authenticate(SignInUserDto signInUserDto);
    void hashPassword(User user, SignUpUserDto signUpUserDto);
}
