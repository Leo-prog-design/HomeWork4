package com.example.HW4;


import com.example.HW4.controller.UserController;
import com.example.HW4.dto.UserDto;
import com.example.HW4.entity.User;
import com.example.HW4.mapper.UserMapper;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class UserModelAssembler implements RepresentationModelAssembler<User, UserDto> {

    private final UserMapper userMapper;

    public UserModelAssembler(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDto toModel(User user) {
        UserDto userDto = userMapper.userToDto(user);

        userDto.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(UserController.class).getUserById(user.getId())).withSelfRel()
        );

        userDto.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(UserController.class).getAllUsers()).withRel("users")
        );

        userDto.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(UserController.class).deleteUser(user.getId())).withRel("delete")
        );

        userDto.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(UserController.class).updateUser(user.getId(), user)).withRel("update")
        );

        userDto.add(WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(UserController.class).createUser(user)).withRel("create")
        );

        return userDto;
    }
}
