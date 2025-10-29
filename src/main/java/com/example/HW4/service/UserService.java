package com.example.HW4.service;

import com.example.HW4.KafkaProducer;
import com.example.HW4.UserModelAssembler;
import com.example.HW4.dto.UserDto;
import com.example.HW4.dto.UserInformationForKafkaDto;
import com.example.HW4.entity.User;
import com.example.HW4.mapper.UserInformationForKafkaMapper;
import com.example.HW4.mapper.UserMapper;
import com.example.HW4.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final UserInformationForKafkaMapper userInformationForKafkaMapper;
    private final KafkaProducer kafkaProducer;
    private final UserModelAssembler assembler;

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    public UserService(
            UserRepository userRepository,
            UserMapper userMapper,
            UserInformationForKafkaMapper userInformationForKafkaMapper,
            KafkaProducer kafkaProducer,
            UserModelAssembler userModelAssembler
    ) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userInformationForKafkaMapper = userInformationForKafkaMapper;
        this.kafkaProducer = kafkaProducer;
        this.assembler = userModelAssembler;
    }

    public UserDto createUser(User user) {
        try {
            User savedUser = userRepository.save(user);
            UserDto userDto = assembler.toModel(savedUser);

            UserInformationForKafkaDto userInfoDto = userInformationForKafkaMapper.userToDto(user);
            userInfoDto.setStatus("Создан");

            kafkaProducer.sendUserInfoKafka(userInfoDto);
            return userDto;
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

            return assembler.toModel(userRepository.save(newUser));

        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка, пользователь не изменён");
        }
    }

    public List<UserDto> readUsers() {
        try {
            List<User> users = userRepository.findAll();
            return users.stream()
                    .map(assembler::toModel)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка");
        }
    }

    public UserDto readUserById(Long id) {
        try {
            return assembler.toModel(
                    userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Пользователь с таким id не найден")));
        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка");
        }
    }

    public void deleteUserById(Long id) {
        try {
            UserInformationForKafkaDto userInfoDto = userInformationForKafkaMapper.userToDto(
                    userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Пользователь с таким id не найден"))
            );

            userRepository.deleteById(id);

            userInfoDto.setStatus("Удалён");
            kafkaProducer.sendUserInfoKafka(userInfoDto);
        } catch (Exception e) {
            throw new IllegalArgumentException("Ошибка, пользователь не удалён");
        }
    }
}