package com.library.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.entity.Author;
import com.library.entity.Book;
import com.library.entity.BookAuthorDTO;
import com.library.exception.DataIntegrityException;
import com.library.exception.ResourceNotFoundException;
import com.library.repository.BookRepository;

@Service
@Transactional
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;

    @Autowired
    public BookService(BookRepository bookRepository, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
    }

    public Book saveBook(Book book, Long authorId) {
        Author author = authorService.getAuthorById(authorId);
        book.setAuthor(author);
        try {
            return bookRepository.save(book);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityException(
                    "Could not save book. Possibly a duplicate ISBN or invalid data: "
                            + ex.getMostSpecificCause().getMessage(),
                    ex);
        }
    }

    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
    }

    @Transactional(readOnly = true)
    public List<BookAuthorDTO> getAllBooksWithAuthors() {
        return bookRepository.findAllBooksWithAuthors();
    }

    @Transactional(readOnly = true)
    public List<BookAuthorDTO> getBooksWithAuthorsByGenre(String genre) {
        return bookRepository.findBooksWithAuthorsByGenre(genre);
    }

    public Book updateBook(Long id, Book updatedBook, Long authorId) {
        Book existing = getBookById(id);
        Author author = authorService.getAuthorById(authorId);

        existing.setTitle(updatedBook.getTitle());
        existing.setIsbn(updatedBook.getIsbn());
        existing.setPublicationYear(updatedBook.getPublicationYear());
        existing.setGenre(updatedBook.getGenre());
        existing.setPrice(updatedBook.getPrice());
        existing.setAuthor(author);

        try {
            return bookRepository.save(existing);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityException(
                    "Could not update book. Possibly a duplicate ISBN or invalid data: "
                            + ex.getMostSpecificCause().getMessage(),
                    ex);
        }
    }
}
