package com.library.repository;

import com.library.entity.Book;
import com.library.entity.BookAuthorDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Book entity.
 * Includes a custom INNER JOIN query method.
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Find a book by its ISBN.
     *
     * @param isbn the ISBN to search for
     * @return Optional containing the book if found
     */
    Optional<Book> findByIsbn(String isbn);

    /**
     * Find all books by a specific author ID.
     *
     * @param authorId the author's ID
     * @return list of books written by that author
     */
    List<Book> findByAuthorId(Long authorId);

    /**
     * CUSTOM QUERY: Performs an INNER JOIN between books and authors.
     * Returns combined book and author information as a DTO.
     *
     * @return list of BookAuthorDTO containing joined data
     */
    @Query("SELECT new com.library.entity.BookAuthorDTO(" +
           "b.id, b.title, b.isbn, b.publicationYear, b.genre, b.price, " +
           "a.id, a.name, a.nationality) " +
           "FROM Book b INNER JOIN b.author a " +
           "ORDER BY a.name ASC, b.title ASC")
    List<BookAuthorDTO> findAllBooksWithAuthors();

    /**
     * CUSTOM QUERY: Inner join filtered by genre.
     *
     * @param genre the genre to filter by
     * @return list of BookAuthorDTO matching the genre
     */
    @Query("SELECT new com.library.entity.BookAuthorDTO(" +
           "b.id, b.title, b.isbn, b.publicationYear, b.genre, b.price, " +
           "a.id, a.name, a.nationality) " +
           "FROM Book b INNER JOIN b.author a " +
           "WHERE b.genre = :genre " +
           "ORDER BY a.name ASC")
    List<BookAuthorDTO> findBooksWithAuthorsByGenre(@Param("genre") String genre);
}
