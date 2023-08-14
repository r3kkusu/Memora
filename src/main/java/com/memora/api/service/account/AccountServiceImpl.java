package com.memora.api.service.account;

import com.memora.api.data.dto.LoginUserDto;
import com.memora.api.data.dto.RegisterUserDto;
import com.memora.api.data.mapper.UserMapper;
import com.memora.api.data.model.User;
import com.memora.api.data.repository.UserRepository;
import com.memora.api.exception.UserException;
import com.memora.api.validator.AuthValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(RegisterUserDto registerUserDto) throws UserException {
        User user = UserMapper.INSTANCE.userDtoToUser(registerUserDto);

        if (userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail())) {
            throw new UserException("Username or email already taken. Please choose different credentials.");
        }

        if (!registerUserDto.getPassword().equals(registerUserDto.getConfirmPassword()) || !AuthValidator.isValidPassword(registerUserDto.getPassword())) {
            throw new UserException("User password is not valid. Please input a valid password");
        }

        String passwordSalt = BCrypt.gensalt();
        String passwordHash = passwordEncoder.encode(registerUserDto.getPassword() + passwordSalt);

        user.setPasswordSalt(passwordSalt);
        user.setPasswordHash(passwordHash);

        userRepository.save(user);
    }

    public boolean authenticateUser(LoginUserDto loginDto) {
        User user = userRepository.findByEmail(loginDto.getEmail());
        if (user == null) {
            return false;
        }

        String saltedPassword = loginDto.getPassword() + user.getPasswordSalt();
        return passwordEncoder.matches(saltedPassword, user.getPasswordHash());
    }
}
