package com.memora.api.data.mapper;

import com.memora.api.data.dto.SignUpUserDto;
import com.memora.api.data.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "id", target = "id")
    SignUpUserDto userToUserDTO(User user);

    @Mapping(source = "id", target = "id")
    User userDtoToUser(SignUpUserDto signUpUserDto);
}
