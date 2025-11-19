package ru.deni1000.bookservice.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.deni1000.bookservice.client.UserServiceClient;
import ru.deni1000.bookservice.dto.AccountDto;
import ru.deni1000.bookservice.dto.BookDto;
import ru.deni1000.bookservice.dto.CreateBookRequest;
import ru.deni1000.bookservice.mapper.BookMapper;
import ru.deni1000.bookservice.model.Book;
import ru.deni1000.bookservice.model.Book.BookStatus;
import ru.deni1000.bookservice.repository.BookRepository;

import java.util.List;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final UserServiceClient userServiceClient;

    public BookService(BookRepository bookRepository, BookMapper bookMapper, UserServiceClient userServiceClient) {
        this.bookRepository = bookRepository;
        this.bookMapper = bookMapper;
        this.userServiceClient = userServiceClient;
    }

    public List<BookDto> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        List<BookDto> bookDtos = bookMapper.toDtoList(books);
        enrichWithUserNames(bookDtos);
        return bookDtos;
    }

    public BookDto getBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + id));
        BookDto bookDto = bookMapper.toDto(book);
        enrichWithUserName(bookDto);
        return bookDto;
    }

    public BookDto createBook(CreateBookRequest request) {
        Book book = new Book(request.getTitle(), request.getAuthor());
        Book savedBook = bookRepository.save(book);
        return bookMapper.toDto(savedBook);
    }

    public BookDto borrowBook(Long bookId, Long userId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookId));

        if (book.getStatus() != BookStatus.AVAILABLE) {
            throw new RuntimeException("Book with id " + bookId + " is not available for borrowing. Current status: " + book.getStatus());
        }

        try {
            userServiceClient.getUserById(userId);
        } catch (Exception e) {
            throw new EntityNotFoundException("User not found with id: " + userId);
        }

        book.setStatus(BookStatus.BORROWED);
        book.setBorrowedByUserId(userId);
        Book savedBook = bookRepository.save(book);

        BookDto bookDto = bookMapper.toDto(savedBook);
        enrichWithUserName(bookDto);
        return bookDto;
    }

    public BookDto returnBook(Long bookId, Long userId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new EntityNotFoundException("Book not found with id: " + bookId));

        if (book.getStatus() != BookStatus.BORROWED) {
            throw new RuntimeException("Book with id " + bookId + " is not borrowed. Current status: " + book.getStatus());
        }

        if (!userId.equals(book.getBorrowedByUserId())) {
            throw new RuntimeException("Book with id " + bookId + " is not borrowed by user with id " + userId + ". It is borrowed by user with id " + book.getBorrowedByUserId());
        }

        book.setStatus(BookStatus.AVAILABLE);
        book.setBorrowedByUserId(null);
        Book savedBook = bookRepository.save(book);

        return bookMapper.toDto(savedBook);
    }

    public List<BookDto> getAvailableBooks() {
        List<Book> books = bookRepository.findByStatus(BookStatus.AVAILABLE);
        return bookMapper.toDtoList(books);
    }

    public List<BookDto> getBorrowedBooks() {
        List<Book> books = bookRepository.findByStatus(BookStatus.BORROWED);
        List<BookDto> bookDtos = bookMapper.toDtoList(books);
        enrichWithUserNames(bookDtos);
        return bookDtos;
    }

    public List<BookDto> getBooksByUser(Long userId) {
        List<Book> books = bookRepository.findByBorrowedByUserId(userId);
        List<BookDto> bookDtos = bookMapper.toDtoList(books);
        enrichWithUserNames(bookDtos);
        return bookDtos;
    }

    public List<BookDto> searchBooks(String query) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(query, query);
        List<BookDto> bookDtos = bookMapper.toDtoList(books);
        enrichWithUserNames(bookDtos);
        return bookDtos;
    }

    private void enrichWithUserNames(List<BookDto> bookDtos) {
        for (BookDto bookDto : bookDtos) {
            enrichWithUserName(bookDto);
        }
    }

    private void enrichWithUserName(BookDto bookDto) {
        if (bookDto.getBorrowedByUserId() == null) {
            return;
        }
        try {
            AccountDto user = userServiceClient.getUserById(bookDto.getBorrowedByUserId());
            bookDto.setBorrowedByUserName(user.getName() + " " + user.getSurname());
        } catch (Exception e) {
            bookDto.setBorrowedByUserName("Unknown User");
        }
    }
}