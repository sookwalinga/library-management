package com.library.repository;

import com.library.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Author entity.
 * Extends JpaRepository to inherit basic CRUD operations.
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    /**
     * Find an author by their exact name.
     *
     * @param name the author's name
     * @return Optional containing the author if found
     */
    Optional<Author> findByName(String name);

    /**
     * Find all authors from a specific nationality.
     *
     * @param nationality the nationality to filter by
     * @return list of authors matching the nationality
     */
    List<Author> findByNationality(String nationality);
}
