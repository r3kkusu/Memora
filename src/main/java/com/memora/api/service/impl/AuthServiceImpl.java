package com.memora.api.service.impl;

import com.memora.api.data.dto.SignInUserDto;
import com.memora.api.data.dto.SignUpUserDto;
import com.memora.api.data.model.User;
import com.memora.api.data.repository.UserRepository;
import com.memora.api.service.AuthService;
import com.memora.api.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public boolean authenticate(SignInUserDto signInUserDto) {
        return userRepository.findByEmail(signInUserDto.getEmail())
                .map(user -> isPasswordValid(signInUserDto.getPassword(), user))
                .orElse(false);
    }

    @Override
    public void hashPassword(User user, SignUpUserDto signUpUserDto) {
        String passwordSalt = BCrypt.gensalt();
        String passwordToHash = signUpUserDto.getPassword() + passwordSalt;
        String passwordHash = passwordEncoder.encode(passwordToHash);

        user.setPasswordSalt(passwordSalt);
        user.setPasswordHash(passwordHash);
    }

    @Override
    public String generateToken(SignInUserDto signInUserDto) {
        var optionalUser = userRepository.findByEmail(signInUserDto.getEmail());
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        var user = optionalUser.get();
        return jwtService.generateToken(user.getUsername());
    }


    private boolean isPasswordValid(String inputPassword, User user) {
        String saltedPassword = inputPassword + user.getPasswordSalt();
        return passwordEncoder.matches(saltedPassword, user.getPasswordHash());
    }
}
