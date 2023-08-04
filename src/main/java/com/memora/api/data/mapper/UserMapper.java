package com.memora.api.data.mapper;

import com.memora.api.data.dto.UserDto;
import com.memora.api.data.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "id", target = "id")
    UserDto userToUserDTO(User user);

    @Mapping(source = "id", target = "id")
    User userDtoToUser(UserDto userDto);
}
