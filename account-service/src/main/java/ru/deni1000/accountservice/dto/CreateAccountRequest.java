package ru.deni1000.accountservice.dto;

import lombok.Data;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Data
public class CreateAccountRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @Min(1)
    private int age;

    public CreateAccountRequest(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public CreateAccountRequest() {
    }
} 