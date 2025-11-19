package ru.deni1000.bookservice.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.deni1000.bookservice.dto.BookDto;
import ru.deni1000.bookservice.dto.BookOperationRequest;
import ru.deni1000.bookservice.dto.CreateBookRequest;
import ru.deni1000.bookservice.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookDto> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable Long id) {
        BookDto book = bookService.getBookById(id);
        return ResponseEntity.ok(book);
    }

    @PostMapping
    public ResponseEntity<BookDto> createBook(@Valid @RequestBody CreateBookRequest request) {
        BookDto book = bookService.createBook(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    @PostMapping("/{id}/borrow")
    public ResponseEntity<BookDto> borrowBook(@PathVariable Long id, @Valid @RequestBody BookOperationRequest request) {
        BookDto book = bookService.borrowBook(id, request.getUserId());
        return ResponseEntity.ok(book);
    }

    @PostMapping("/{id}/return")
    public ResponseEntity<BookDto> returnBook(@PathVariable Long id, @Valid @RequestBody BookOperationRequest request) {
        BookDto book = bookService.returnBook(id, request.getUserId());
        return ResponseEntity.ok(book);
    }

    @GetMapping("/available")
    public ResponseEntity<List<BookDto>> getAvailableBooks() {
        List<BookDto> books = bookService.getAvailableBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/borrowed")
    public ResponseEntity<List<BookDto>> getBorrowedBooks() {
        List<BookDto> books = bookService.getBorrowedBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookDto>> getBooksByUser(@PathVariable Long userId) {
        List<BookDto> books = bookService.getBooksByUser(userId);
        return ResponseEntity.ok(books);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookDto>> searchBooks(@RequestParam String query) {
        List<BookDto> books = bookService.searchBooks(query);
        return ResponseEntity.ok(books);
    }
} 