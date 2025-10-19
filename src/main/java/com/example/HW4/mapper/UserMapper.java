package com.example.HW4.mapper;

import com.example.HW4.dto.UserDto;
import com.example.HW4.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto userToDto(User user);

    @Mapping(target = "id", ignore = true)
    User dtoToUser(UserDto userDto);

}
