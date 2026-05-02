package com.library.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.library.entity.Author;

@DataJpaTest
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
    void testSaveAuthor() {
        Author newAuthor = new Author("Stephen King", "American", 1947, "Horror novelist");
        Author saved = authorRepository.save(newAuthor);

        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Stephen King");
    }

    @Test
    void testFindAllAuthors() {
        List<Author> authors = authorRepository.findAll();
        assertThat(authors).hasSize(2);
    }

    @Test
    void testFindById() {
        Optional<Author> found = authorRepository.findById(author1.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("J.K. Rowling");
    }

    @Test
    void testFindByName() {
        Optional<Author> found = authorRepository.findByName("Mark Twain");
        assertThat(found).isPresent();
        assertThat(found.get().getNationality()).isEqualTo("American");
    }

    @Test
    void testFindByNationality() {
        List<Author> americans = authorRepository.findByNationality("American");
        assertThat(americans).hasSize(1);
        assertThat(americans.get(0).getName()).isEqualTo("Mark Twain");
    }

    @Test
    void testUpdateAuthor() {
        author1.setNationality("English");
        Author updated = authorRepository.save(author1);
        assertThat(updated.getNationality()).isEqualTo("English");
    }
}
