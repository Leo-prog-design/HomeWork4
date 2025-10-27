package com.example.HW4.dto;

public class UserInformationForKafkaDto {
    private String status;
    private String email;

    public UserInformationForKafkaDto() {
    }

    public UserInformationForKafkaDto(String email, String status) {
        this.email = email;
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
