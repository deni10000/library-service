package ru.deni1000.accountservice.dto;

import lombok.Data;

@Data
public class AccountDto {
    private long id;
    private String name;
    private String surname;

    public AccountDto(long id, String name, String surname) {
        this.id = id;
        this.name = name;
        this.surname = surname;
    }

    public AccountDto() {
    }
} 