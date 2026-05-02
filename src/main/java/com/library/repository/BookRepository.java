package com.library.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.library.entity.Book;
import com.library.entity.BookAuthorDTO;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

       Optional<Book> findByIsbn(String isbn);

       List<Book> findByAuthorId(Long authorId);

       // joins books with their authors so I can display both in a single query
       @Query("SELECT new com.library.entity.BookAuthorDTO(" +
                     "b.id, b.title, b.isbn, b.publicationYear, b.genre, b.price, " +
                     "a.id, a.name, a.nationality) " +
                     "FROM Book b INNER JOIN b.author a " +
                     "ORDER BY a.name ASC, b.title ASC")
       List<BookAuthorDTO> findAllBooksWithAuthors();

       @Query("SELECT new com.library.entity.BookAuthorDTO(" +
                     "b.id, b.title, b.isbn, b.publicationYear, b.genre, b.price, " +
                     "a.id, a.name, a.nationality) " +
                     "FROM Book b INNER JOIN b.author a " +
                     "WHERE LOWER(b.genre) = LOWER(:genre) " +
                     "ORDER BY a.name ASC")
       List<BookAuthorDTO> findBooksWithAuthorsByGenre(@Param("genre") String genre);
}
