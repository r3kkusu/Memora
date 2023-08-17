package com.memora.api.service;

import com.memora.api.data.dto.SignUpUserDto;
import com.memora.api.common.exception.UserException;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    void signUp(SignUpUserDto signUpUserDto) throws UserException;
    UserDetailsService userDetailsService();
}
