package com.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author entity representing a book author.
 * One author can write many books (One-to-Many relationship).
 */
@Entity
@Table(name = "authors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Author name cannot be blank")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 100)
    private String nationality;

    @Column(name = "birth_year")
    private Integer birthYear;

    @Column(length = 500)
    private String biography;

    /**
     * One-to-Many relationship with Book.
     * An author can have multiple books.
     */
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Book> books = new ArrayList<>();

    public Author(String name, String nationality, Integer birthYear, String biography) {
        this.name = name;
        this.nationality = nationality;
        this.birthYear = birthYear;
        this.biography = biography;
    }
}
