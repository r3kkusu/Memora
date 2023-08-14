package com.memora.api.service.account;

import com.memora.api.data.dto.LoginUserDto;
import com.memora.api.data.dto.RegisterUserDto;
import com.memora.api.exception.UserException;

public interface AccountService {
    void registerUser(RegisterUserDto registerUserDto) throws UserException;
    boolean authenticateUser(LoginUserDto loginDto);
}
