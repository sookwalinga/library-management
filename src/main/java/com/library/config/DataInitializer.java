package com.library.config;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.library.entity.Author;
import com.library.entity.Book;
import com.library.repository.AuthorRepository;
import com.library.repository.BookRepository;

// Initializes the database with 10 sample authors and 10 sample books on startup.

@Configuration
public class DataInitializer {

        @Bean
        public CommandLineRunner loadData(AuthorRepository authorRepository,
                        BookRepository bookRepository) {
                return args -> {
                        // Skip if data already exists
                        if (authorRepository.count() > 0) {
                                return;
                        }

                        // Create 10 authors
                        List<Author> authors = List.of(
                                        new Author("J.K. Rowling", "British", 1965,
                                                        "Author of the Harry Potter series."),
                                        new Author("George Orwell", "British", 1903,
                                                        "Famous for 1984 and Animal Farm."),
                                        new Author("Harper Lee", "American", 1926, "Author of To Kill a Mockingbird."),
                                        new Author("Jane Austen", "British", 1775, "Renowned for Pride and Prejudice."),
                                        new Author("Mark Twain", "American", 1835,
                                                        "Author of Adventures of Huckleberry Finn."),
                                        new Author("Leo Tolstoy", "Russian", 1828,
                                                        "Author of War and Peace and Anna Karenina."),
                                        new Author("Gabriel Garcia Marquez", "Colombian", 1927,
                                                        "Nobel laureate, magical realism pioneer."),
                                        new Author("Ernest Hemingway", "American", 1899,
                                                        "Famous for The Old Man and the Sea."),
                                        new Author("Agatha Christie", "British", 1890, "Queen of mystery novels."),
                                        new Author("Fyodor Dostoevsky", "Russian", 1821,
                                                        "Author of Crime and Punishment."));

                        List<Author> savedAuthors = authorRepository.saveAll(authors);

                        // Create 10 books, each linked to an author
                        List<Book> books = List.of(
                                        new Book("Harry Potter and the Philosopher's Stone", "978-0747532699",
                                                        1997, "Fantasy", 19.99, savedAuthors.get(0)),
                                        new Book("1984", "978-0451524935",
                                                        1949, "Dystopian", 14.99, savedAuthors.get(1)),
                                        new Book("To Kill a Mockingbird", "978-0061120084",
                                                        1960, "Classic", 12.99, savedAuthors.get(2)),
                                        new Book("Pride and Prejudice", "978-0141439518",
                                                        1813, "Romance", 9.99, savedAuthors.get(3)),
                                        new Book("Adventures of Huckleberry Finn", "978-0486280615",
                                                        1884, "Classic", 10.99, savedAuthors.get(4)),
                                        new Book("War and Peace", "978-1400079988",
                                                        1869, "Historical", 24.99, savedAuthors.get(5)),
                                        new Book("One Hundred Years of Solitude", "978-0060883287",
                                                        1967, "Magical Realism", 16.99, savedAuthors.get(6)),
                                        new Book("The Old Man and the Sea", "978-0684801223",
                                                        1952, "Classic", 11.99, savedAuthors.get(7)),
                                        new Book("Murder on the Orient Express", "978-0062073501",
                                                        1934, "Mystery", 13.99, savedAuthors.get(8)),
                                        new Book("Crime and Punishment", "978-0140449136",
                                                        1866, "Classic", 15.99, savedAuthors.get(9)));

                        bookRepository.saveAll(books);

                        System.out.println("======================================");
                        System.out.println("Database populated with sample data!");
                        System.out.println("Authors: " + authorRepository.count());
                        System.out.println("Books: " + bookRepository.count());
                        System.out.println("======================================");
                };
        }
}
