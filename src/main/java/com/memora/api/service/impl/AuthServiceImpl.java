package com.memora.api.service.impl;

import com.memora.api.data.dto.SignInUserDto;
import com.memora.api.data.dto.SignUpUserDto;
import com.memora.api.data.model.User;
import com.memora.api.data.repository.UserRepository;
import com.memora.api.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    final private UserRepository userRepository;
    final private PasswordEncoder passwordEncoder;

    @Override
    public boolean authenticate(SignInUserDto signInUserDto) {
        User user = userRepository.findByEmail(signInUserDto.getEmail());
        if (user == null) {
            return false;
        }

        String saltedPassword = signInUserDto.getPassword() + user.getPasswordSalt();
        return passwordEncoder.matches(saltedPassword, user.getPasswordHash());
    }

    @Override
    public void hashPassword(User user, SignUpUserDto signUpUserDto) {
        String passwordSalt = BCrypt.gensalt();
        String passwordHash = passwordEncoder.encode(signUpUserDto.getPassword() + passwordSalt);

        user.setPasswordSalt(passwordSalt);
        user.setPasswordHash(passwordHash);
    }
}
