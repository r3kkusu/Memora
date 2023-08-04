package com.memora.api.service.account;

import com.memora.api.data.dto.UserDto;
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
public class AccountService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    private AccountService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(UserDto userDto) throws UserException {
        User user = UserMapper.INSTANCE.userDtoToUser(userDto);

        if (userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail())) {
            throw new UserException("Username or email already taken. Please choose different credentials.");
        }

        if (!userDto.getPassword().equals(userDto.getConfirmPassword()) || !AuthValidator.isValidPassword(userDto.getPassword())) {
            throw new UserException("User password is not valid. Please input a valid password");
        }

        String passwordSalt = BCrypt.gensalt();
        String passwordHash = passwordEncoder.encode(userDto.getPassword() + passwordSalt);

        user.setPasswordSalt(passwordSalt);
        user.setPasswordHast(passwordHash);

        userRepository.save(user);
    }
}
