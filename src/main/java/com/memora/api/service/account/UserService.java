package com.memora.api.service.account;

import com.memora.api.data.dto.UserDto;
import com.memora.api.data.mapper.UserMapper;
import com.memora.api.data.model.User;
import com.memora.api.data.repository.UserRepository;
import com.memora.api.exception.DatabaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    private UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(UserDto userDto) throws DatabaseException {
        User user = UserMapper.INSTANCE.userDtoToUser(userDto);
        if (userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail())) {
            throw new DatabaseException("Username or email already taken. Please choose different credentials.");
        }

        return userRepository.save(user);
    }
}
