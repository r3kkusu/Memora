package com.memora.api.service.impl;

import com.memora.api.data.dto.SignUpUserDto;
import com.memora.api.data.mapper.UserMapper;
import com.memora.api.data.model.User;
import com.memora.api.data.repository.UserRepository;
import com.memora.api.common.exception.UserException;
import com.memora.api.common.validator.AuthValidator;
import com.memora.api.service.AuthService;
import com.memora.api.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuthService authService;

    public void signUp(SignUpUserDto signUpUserDto) throws UserException {
        User user = UserMapper.INSTANCE.userDtoToUser(signUpUserDto);

        if (userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail())) {
            throw new UserException("Username or email already taken. Please choose different credentials.");
        }

        if (!signUpUserDto.getPassword().equals(signUpUserDto.getConfirmPassword()) || !AuthValidator.isValidPassword(signUpUserDto.getPassword())) {
            throw new UserException("User password is not valid. Please input a valid password");
        }

        authService.hashPassword(user, signUpUserDto);
        userRepository.save(user);
    }
}
