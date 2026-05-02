package com.library.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.library.entity.Author;
import com.library.exception.DataIntegrityException;
import com.library.exception.ResourceNotFoundException;
import com.library.repository.AuthorRepository;

/**
 * Service class for Author business logic.
 * Acts as a layer between the controller and the repository.
 */
@Service
@Transactional
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    /**
     * Save a new author with integrity violation handling.
     *
     * @param author the author to save
     * @return the saved author
     */
    public Author saveAuthor(Author author) {
        try {
            return authorRepository.save(author);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityException(
                    "Could not save author due to a data integrity violation: "
                            + ex.getMostSpecificCause().getMessage(),
                    ex);
        }
    }

    /**
     * Get all authors.
     *
     * @return list of all authors
     */
    @Transactional(readOnly = true)
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    /**
     * Get an author by ID.
     *
     * @param id the author ID
     * @return the author
     * @throws ResourceNotFoundException if not found
     */
    @Transactional(readOnly = true)
    public Author getAuthorById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author", "id", id));
    }

    /**
     * Update an existing author.
     *
     * @param id            the ID of the author to update
     * @param updatedAuthor the new author data
     * @return the updated author
     */
    public Author updateAuthor(Long id, Author updatedAuthor) {
        Author existing = getAuthorById(id);
        existing.setName(updatedAuthor.getName());
        existing.setNationality(updatedAuthor.getNationality());
        existing.setBirthYear(updatedAuthor.getBirthYear());
        existing.setBiography(updatedAuthor.getBiography());

        try {
            return authorRepository.save(existing);
        } catch (DataIntegrityViolationException ex) {
            throw new DataIntegrityException(
                    "Could not update author due to a data integrity violation: "
                            + ex.getMostSpecificCause().getMessage(),
                    ex);
        }
    }
}
