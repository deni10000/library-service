package ru.deni1000.bookservice.dto;

import lombok.Data;
import jakarta.validation.constraints.NotNull;

@Data
public class BookOperationRequest {
    @NotNull(message = "User ID is required")
    private Long userId;

    public BookOperationRequest() {
    }

    public BookOperationRequest(Long userId) {
        this.userId = userId;
    }
} 