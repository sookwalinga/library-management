package com.library.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import com.library.entity.Author;
import com.library.entity.Book;
import com.library.entity.BookAuthorDTO;
import com.library.exception.DataIntegrityException;
import com.library.exception.ResourceNotFoundException;
import com.library.repository.BookRepository;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private BookService bookService;

    private Author author;
    private Book book;

    @BeforeEach
    void setUp() {
        author = new Author("J.K. Rowling", "British", 1965, "Bio");
        author.setId(1L);

        book = new Book("Harry Potter", "ISBN-001", 1997, "Fantasy", 19.99, author);
        book.setId(1L);
    }

    @Test
    void testSaveBook() {
        when(authorService.getAuthorById(1L)).thenReturn(author);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book saved = bookService.saveBook(book, 1L);

        assertThat(saved.getTitle()).isEqualTo("Harry Potter");
        assertThat(saved.getAuthor()).isEqualTo(author);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void testSaveBook_DataIntegrityException() {
        when(authorService.getAuthorById(1L)).thenReturn(author);
        when(bookRepository.save(any(Book.class)))
                .thenThrow(new DataIntegrityViolationException("duplicate ISBN"));

        assertThrows(DataIntegrityException.class,
                () -> bookService.saveBook(book, 1L));
    }

    @Test
    void testGetAllBooks() {
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book));

        List<Book> books = bookService.getAllBooks();

        assertThat(books).hasSize(1);
    }

    @Test
    void testGetBookById_Found() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Book result = bookService.getBookById(1L);

        assertThat(result.getTitle()).isEqualTo("Harry Potter");
    }

    @Test
    void testGetBookById_NotFound() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> bookService.getBookById(99L));
    }

    @Test
    void testGetAllBooksWithAuthors() {
        BookAuthorDTO dto = new BookAuthorDTO(
                1L, "Harry Potter", "ISBN-001", 1997, "Fantasy", 19.99,
                1L, "J.K. Rowling", "British");
        when(bookRepository.findAllBooksWithAuthors()).thenReturn(Arrays.asList(dto));

        List<BookAuthorDTO> results = bookService.getAllBooksWithAuthors();

        assertThat(results).hasSize(1);
        assertThat(results.get(0).getAuthorName()).isEqualTo("J.K. Rowling");
        verify(bookRepository, times(1)).findAllBooksWithAuthors();
    }

    @Test
    void testUpdateBook() {
        Book updated = new Book("Updated Title", "ISBN-002", 2000,
                "Adventure", 25.0, null);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(authorService.getAuthorById(1L)).thenReturn(author);
        when(bookRepository.save(any(Book.class))).thenReturn(book);

        Book result = bookService.updateBook(1L, updated, 1L);

        assertThat(result.getTitle()).isEqualTo("Updated Title");
        assertThat(result.getIsbn()).isEqualTo("ISBN-002");
        verify(bookRepository, times(1)).save(any(Book.class));
    }
}
