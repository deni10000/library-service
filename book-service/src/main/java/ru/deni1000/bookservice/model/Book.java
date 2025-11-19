package ru.deni1000.bookservice.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String title;
    private String author;

    @Enumerated(EnumType.STRING)
    private BookStatus status;

    private Long borrowedByUserId;

    public Book() {
        this.status = BookStatus.AVAILABLE;
    }

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
        this.status = BookStatus.AVAILABLE;
    }

    public enum BookStatus {
        AVAILABLE,
        BORROWED
    }
} 