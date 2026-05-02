package com.library.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import com.library.entity.Author;
import com.library.exception.DataIntegrityException;
import com.library.exception.ResourceNotFoundException;
import com.library.repository.AuthorRepository;

/**
 * Unit tests for the AuthorService using Mockito to mock the repository.
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("AuthorService Tests")
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorService authorService;

    private Author author;

    @BeforeEach
    void setUp() {
        author = new Author("J.K. Rowling", "British", 1965, "Bio");
        author.setId(1L);
    }

    @Test
    @DisplayName("saveAuthor() should call repository.save() and return saved author")
    void testSaveAuthor() {
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        Author saved = authorService.saveAuthor(author);

        assertThat(saved).isNotNull();
        assertThat(saved.getName()).isEqualTo("J.K. Rowling");
        verify(authorRepository, times(1)).save(author);
    }

    @Test
    @DisplayName("saveAuthor() should wrap DataIntegrityViolationException in custom exception")
    void testSaveAuthor_DataIntegrityException() {
        when(authorRepository.save(any(Author.class)))
                .thenThrow(new DataIntegrityViolationException("duplicate key"));

        assertThrows(DataIntegrityException.class, () -> authorService.saveAuthor(author));
        verify(authorRepository, times(1)).save(author);
    }

    @Test
    @DisplayName("getAllAuthors() should return list from repository")
    void testGetAllAuthors() {
        Author author2 = new Author("Mark Twain", "American", 1835, "Bio");
        when(authorRepository.findAll()).thenReturn(Arrays.asList(author, author2));

        List<Author> authors = authorService.getAllAuthors();

        assertThat(authors).hasSize(2);
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("getAuthorById() should return author when found")
    void testGetAuthorById_Found() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        Author result = authorService.getAuthorById(1L);

        assertThat(result.getName()).isEqualTo("J.K. Rowling");
    }

    @Test
    @DisplayName("getAuthorById() should throw ResourceNotFoundException when not found")
    void testGetAuthorById_NotFound() {
        when(authorRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> authorService.getAuthorById(99L));
    }

    @Test
    @DisplayName("updateAuthor() should update existing fields and save")
    void testUpdateAuthor() {
        Author updates = new Author("J.K. Rowling Updated", "English", 1965, "New bio");

        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        Author result = authorService.updateAuthor(1L, updates);

        assertThat(result.getName()).isEqualTo("J.K. Rowling Updated");
        assertThat(result.getNationality()).isEqualTo("English");
        verify(authorRepository, times(1)).save(any(Author.class));
    }
}
