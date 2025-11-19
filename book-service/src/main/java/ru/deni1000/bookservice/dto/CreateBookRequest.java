package ru.deni1000.bookservice.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class CreateBookRequest {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Author is required")
    private String author;

    public CreateBookRequest() {
    }

    public CreateBookRequest(String title, String author) {
        this.title = title;
        this.author = author;
    }
} 