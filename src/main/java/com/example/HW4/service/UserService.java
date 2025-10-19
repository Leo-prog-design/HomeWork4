package com.example.HW4.service;

import com.example.HW4.dto.UserDto;
import com.example.HW4.entity.User;
import com.example.HW4.mapper.UserMapper;
import com.example.HW4.repository.UserRepository;
import com.sun.tools.javac.Main;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public UserDto createUser(User user) {
        try {
            return userMapper.userToDto(userRepository.save(user));
        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка, новый пользователь не создан");
        }
    }

    public UserDto updateUserById(Long id, User user) {
        try {
            User newUser = userRepository.findById(id).
                    orElseThrow(() -> new EntityNotFoundException("Пользователь с таким id не найден"));

            newUser.setName(user.getName());
            newUser.setAge(user.getAge());


            return userMapper.userToDto(userRepository.save(newUser));

        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка, пользователь не изменён");
        }

    }

    public List<UserDto> readUsers() {
        try {
            List<User> users = userRepository.findAll();
            return users.stream()
                    .map(userMapper::userToDto)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка");
        }
    }

    public void deleteUserById(Long id) {
        try {
            userRepository.deleteById(id);
        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка, пользователь не удалён");
        }
    }
}