package com.library.repository;

import com.library.entity.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the AuthorRepository.
 * Uses @DataJpaTest for testing the persistence layer with an in-memory DB.
 */
@DataJpaTest
@DisplayName("AuthorRepository Tests")
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    private Author author1;
    private Author author2;

    @BeforeEach
    void setUp() {
        authorRepository.deleteAll();
        author1 = new Author("J.K. Rowling", "British", 1965, "Harry Potter author");
        author2 = new Author("Mark Twain", "American", 1835, "Adventures of Huck Finn");
        authorRepository.save(author1);
        authorRepository.save(author2);
    }

    @Test
    @DisplayName("save() should persist a new author successfully")
    void testSaveAuthor() {
        Author newAuthor = new Author("Stephen King", "American", 1947, "Horror novelist");
        Author saved = authorRepository.save(newAuthor);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Stephen King");
    }

    @Test
    @DisplayName("findAll() should return all saved authors")
    void testFindAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        assertThat(authors).hasSize(2);
    }

    @Test
    @DisplayName("findById() should return an author when ID exists")
    void testFindById() {
        Optional<Author> found = authorRepository.findById(author1.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("J.K. Rowling");
    }

    @Test
    @DisplayName("findByName() should return correct author")
    void testFindByName() {
        Optional<Author> found = authorRepository.findByName("Mark Twain");
        assertThat(found).isPresent();
        assertThat(found.get().getNationality()).isEqualTo("American");
    }

    @Test
    @DisplayName("findByNationality() should return matching authors")
    void testFindByNationality() {
        List<Author> americans = authorRepository.findByNationality("American");
        assertThat(americans).hasSize(1);
        assertThat(americans.get(0).getName()).isEqualTo("Mark Twain");
    }

    @Test
    @DisplayName("update should change author details when saved again")
    void testUpdateAuthor() {
        author1.setNationality("English");
        Author updated = authorRepository.save(author1);
        assertThat(updated.getNationality()).isEqualTo("English");
    }
}
