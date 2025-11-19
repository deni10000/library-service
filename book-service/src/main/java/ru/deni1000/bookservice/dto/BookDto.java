package ru.deni1000.bookservice.dto;

import lombok.Data;
import ru.deni1000.bookservice.model.Book.BookStatus;

@Data
public class BookDto {
    private long id;
    private String title;
    private String author;
    private BookStatus status;
    private Long borrowedByUserId;
    private String borrowedByUserName;

    public BookDto() {
    }

    public BookDto(long id, String title, String author, BookStatus status, Long borrowedByUserId) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.status = status;
        this.borrowedByUserId = borrowedByUserId;
    }
} 