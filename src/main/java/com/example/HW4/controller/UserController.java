package com.example.HW4.controller;

import com.example.HW4.dto.UserDto;
import com.example.HW4.entity.User;
import com.example.HW4.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController implements UserControllerApi {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Override
    public ResponseEntity<UserDto> createUser(User user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<UserDto>> getAllUsers() {
        return new ResponseEntity<>(userService.readUsers(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserDto> updateUser(Long id, User user) {
        return new ResponseEntity<>(userService.updateUserById(id, user), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserDto> deleteUser(Long id) {
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
