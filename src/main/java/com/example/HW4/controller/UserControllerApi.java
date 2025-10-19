package com.example.HW4.controller;

import com.example.HW4.dto.UserDto;
import com.example.HW4.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="Users", description = "API для управления пользователями")
public interface UserControllerApi {
    @Operation(
            summary = "Создание нового пользователя",
            description = "API метод для создания нового пользователя в системе"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Книга успешно создана",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некоректные данные для запроса",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Object.class))
            )
    })
    @PostMapping(value = "/api/createuser", produces = "application/json", consumes = "application/json")
    ResponseEntity<UserDto> createUser(
        @Parameter(description = "Данные для создания пользователь", required = true)
        @Valid @RequestBody User user
    );


    @Operation(
            summary = "Получение всех пользователей",
            description = "API метод для получения списка всех пользователей"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Список пользователей успешно получен",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto[].class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователи не найдены",
                    content = @Content(mediaType = "application/json")
            )
    })
    @GetMapping(value = "/api/users/{id}", produces = "application/json")
    ResponseEntity<List<UserDto>> getAllUsers();


    @Operation(
            summary = "Обновление пользователя",
            description = "API метод для обновления данных пользователя"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Пользователь успешно обновлен",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь с указанным ID не найден",
                    content = @Content(mediaType = "application/json")
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Некорректные данные для запроса",
                    content = @Content(mediaType = "application/json")
            )
    })
    @PutMapping(value = "/api/users/{id}", produces = "application/json", consumes = "application/json")
    ResponseEntity<UserDto> updateUser(
            @Parameter(description = "ID пользователя", required = true)
            @PathVariable Long id,
            @Parameter(description = "Новые данные пользователя", required = true)
            @Valid @RequestBody User user
    );


    @Operation(
            summary = "Удаление пользователя",
            description = "API метод для обновления данных пользователя"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Пользователь успешно удален"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Пользователь с указанным ID не найден",
                    content = @Content(mediaType = "application/json")
            )
    })
    @DeleteMapping(value = "/api/users/{id}")
    ResponseEntity<UserDto> deleteUser(
            @Parameter(description = "ID пользователя", required = true)
            @PathVariable Long id
    );
}
