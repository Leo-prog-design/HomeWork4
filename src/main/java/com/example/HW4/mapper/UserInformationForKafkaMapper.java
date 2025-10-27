package com.example.HW4.mapper;

import com.example.HW4.dto.UserInformationForKafkaDto;
import com.example.HW4.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserInformationForKafkaMapper {

    @Mapping(target = "status", ignore = true)
    UserInformationForKafkaDto userToDto(User user);

    @Mapping(target = "id", ignore = true)
    User dtoToUser(UserInformationForKafkaDto userInfoDto);

}
