package com.example.HW4.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Schema(description = "Информация о пользователе")
@Relation(collectionRelation = "users")
public class UserDto extends RepresentationModel<UserDto> {

    @NotBlank(message="Имя обязательно")
    @Schema(description = "Имя пользователя")
    private String name;

    @Pattern(regexp="^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\\\.[A-Za-z]{2,}$", message = "Неправильный формат почты")
    @Schema(description = "Почта")
    private String email;

    @Schema(description = "Возраст пользователя")
    private Integer age;

    public UserDto() {
    }

    public UserDto(Integer age, String email, String name) {
        this.age = age;
        this.email = email;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
