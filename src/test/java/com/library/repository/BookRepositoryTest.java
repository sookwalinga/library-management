package com.library.repository;

import com.library.entity.Author;
import com.library.entity.Book;
import com.library.entity.BookAuthorDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for BookRepository, including the custom INNER JOIN query.
 */
@DataJpaTest
@DisplayName("BookRepository Tests")
class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private AuthorRepository authorRepository;

    private Author author;
    private Book book1;
    private Book book2;

    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();

        author = new Author("J.K. Rowling", "British", 1965, "Harry Potter author");
        author = authorRepository.save(author);

        book1 = new Book("Harry Potter 1", "ISBN-001", 1997, "Fantasy", 19.99, author);
        book2 = new Book("Harry Potter 2", "ISBN-002", 1998, "Fantasy", 21.99, author);
        bookRepository.save(book1);
        bookRepository.save(book2);
    }

    @Test
    @DisplayName("save() should persist a new book")
    void testSaveBook() {
        Book newBook = new Book("New Book", "ISBN-NEW", 2024, "Fiction", 15.00, author);
        Book saved = bookRepository.save(newBook);
        assertThat(saved.getId()).isNotNull();
    }

    @Test
    @DisplayName("findAll() should return all saved books")
    void testFindAllBooks() {
        List<Book> books = bookRepository.findAll();
        assertThat(books).hasSize(2);
    }

    @Test
    @DisplayName("findByIsbn() should return correct book")
    void testFindByIsbn() {
        Optional<Book> found = bookRepository.findByIsbn("ISBN-001");
        assertThat(found).isPresent();
        assertThat(found.get().getTitle()).isEqualTo("Harry Potter 1");
    }

    @Test
    @DisplayName("findByAuthorId() should return books by that author")
    void testFindByAuthorId() {
        List<Book> books = bookRepository.findByAuthorId(author.getId());
        assertThat(books).hasSize(2);
    }

    @Test
    @DisplayName("Custom inner join query should return all book-author pairs")
    void testFindAllBooksWithAuthors() {
        List<BookAuthorDTO> results = bookRepository.findAllBooksWithAuthors();
        assertThat(results).hasSize(2);

        BookAuthorDTO first = results.get(0);
        assertThat(first.getBookTitle()).isNotNull();
        assertThat(first.getAuthorName()).isEqualTo("J.K. Rowling");
        assertThat(first.getNationality()).isEqualTo("British");
    }

    @Test
    @DisplayName("Inner join with genre filter should return only matching books")
    void testFindBooksWithAuthorsByGenre() {
        List<BookAuthorDTO> results = bookRepository.findBooksWithAuthorsByGenre("Fantasy");
        assertThat(results).hasSize(2);
        assertThat(results).allMatch(dto -> "Fantasy".equals(dto.getGenre()));
    }

    @Test
    @DisplayName("Saving a book with a duplicate ISBN should throw DataIntegrityViolationException")
    void testDuplicateIsbnThrowsException() {
        Book duplicate = new Book("Another Book", "ISBN-001", 2020, "Fiction", 10.0, author);
        assertThrows(DataIntegrityViolationException.class,
                () -> bookRepository.saveAndFlush(duplicate));
    }
}
