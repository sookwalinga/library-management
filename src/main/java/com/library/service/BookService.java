package com.library.service;

import com.library.entity.Author;
import com.library.entity.Book;
import com.library.entity.BookAuthorDTO;
import com.library.exception.DataIntegrityException;
import com.library.exception.ResourceNotFoundException;
import com.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service class for Book business logic.
 */
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

    /**
     * Save a new book with author association.
     *
     * @param book the book to save
     * @param authorId the ID of the associated author
     * @return the saved book
     */
    public Book saveBook(Book book, Long authorId) {
        Author author = authorService.getAuthorById(authorId);
        book.setAuthor(author);
        try {
            return bookRepository.save(book);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityException(
                    "Could not save book. Possibly a duplicate ISBN or invalid data: "
                            + ex.getMostSpecificCause().getMessage(),
                    ex
            );
        }
    }

    /**
     * Get all books.
     *
     * @return list of all books
     */
    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    /**
     * Get a book by ID.
     *
     * @param id the book ID
     * @return the book
     */
    @Transactional(readOnly = true)
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book", "id", id));
    }

    /**
     * Get all books with their author info using INNER JOIN.
     *
     * @return list of joined book-author DTOs
     */
    @Transactional(readOnly = true)
    public List<BookAuthorDTO> getAllBooksWithAuthors() {
        return bookRepository.findAllBooksWithAuthors();
    }

    /**
     * Get books with authors filtered by genre.
     *
     * @param genre the genre to filter by
     * @return filtered list of joined records
     */
    @Transactional(readOnly = true)
    public List<BookAuthorDTO> getBooksWithAuthorsByGenre(String genre) {
        return bookRepository.findBooksWithAuthorsByGenre(genre);
    }

    /**
     * Update an existing book.
     *
     * @param id the book ID
     * @param updatedBook the new book data
     * @param authorId the (possibly new) author ID
     * @return the updated book
     */
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
                    ex
            );
        }
    }
}
