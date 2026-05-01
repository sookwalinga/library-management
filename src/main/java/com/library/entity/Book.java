package com.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Book entity representing a book in the library.
 * Many books can belong to one author (Many-to-One relationship).
 */
@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title cannot be blank")
    @Size(min = 1, max = 200, message = "Title must be between 1 and 200 characters")
    @Column(nullable = false, length = 200)
    private String title;

    @NotBlank(message = "ISBN cannot be blank")
    @Column(unique = true, nullable = false, length = 20)
    private String isbn;

    @NotNull(message = "Publication year is required")
    @Column(name = "publication_year")
    private Integer publicationYear;

    @Size(max = 50, message = "Genre must be at most 50 characters")
    @Column(length = 50)
    private String genre;

    @Positive(message = "Price must be positive")
    private Double price;

    /**
     * Many-to-One relationship with Author.
     * Each book has exactly one author.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    public Book(String title, String isbn, Integer publicationYear, String genre, Double price, Author author) {
        this.title = title;
        this.isbn = isbn;
        this.publicationYear = publicationYear;
        this.genre = genre;
        this.price = price;
        this.author = author;
    }
}
