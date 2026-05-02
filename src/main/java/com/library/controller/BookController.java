package com.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.library.entity.Book;
import com.library.exception.DataIntegrityException;
import com.library.service.AuthorService;
import com.library.service.BookService;

import jakarta.validation.Valid;

/**
 * Spring MVC Controller for Book-related operations.
 * Handles create, read, and update HTTP requests.
 */
@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    @Autowired
    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    // READ: Display the list of all books with their authors using INNER JOIN.
    @GetMapping
    public String listBooks(@RequestParam(value = "genre", required = false) String genre,
            Model model) {
        if (genre != null && !genre.isBlank()) {
            model.addAttribute("books", bookService.getBooksWithAuthorsByGenre(genre));
            model.addAttribute("filterGenre", genre);
        } else {
            model.addAttribute("books", bookService.getAllBooksWithAuthors());
        }
        return "books/list";
    }

    // CREATE: Show the form to add a new book.
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authorService.getAllAuthors());
        return "books/form";
    }

    // CREATE: Handle book creation form submission.
    @PostMapping
    public String createBook(@Valid @ModelAttribute("book") Book book,
            BindingResult bindingResult,
            @RequestParam("authorId") Long authorId,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("authors", authorService.getAllAuthors());
            return "books/form";
        }
        try {
            bookService.saveBook(book, authorId);
            return "redirect:/books";
        } catch (DataIntegrityException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("authors", authorService.getAllAuthors());
            return "books/form";
        }
    }

    // UPDATE: Show the form to edit an existing book.
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id);
        model.addAttribute("book", book);
        model.addAttribute("authors", authorService.getAllAuthors());
        return "books/edit";
    }

    // UPDATE: Handle book update form submission.
    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable Long id,
            @Valid @ModelAttribute("book") Book book,
            BindingResult bindingResult,
            @RequestParam("authorId") Long authorId,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("authors", authorService.getAllAuthors());
            return "books/edit";
        }
        try {
            bookService.updateBook(id, book, authorId);
            return "redirect:/books";
        } catch (DataIntegrityException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("authors", authorService.getAllAuthors());
            return "books/edit";
        }
    }
}
