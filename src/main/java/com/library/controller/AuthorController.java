package com.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.library.entity.Author;
import com.library.exception.DataIntegrityException;
import com.library.service.AuthorService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public String listAuthors(Model model) {
        model.addAttribute("authors", authorService.getAllAuthors());
        return "authors/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("author", new Author());
        return "authors/form";
    }

    @PostMapping
    public String createAuthor(@Valid @ModelAttribute("author") Author author,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "authors/form";
        }
        try {
            authorService.saveAuthor(author);
            return "redirect:/authors";
        } catch (DataIntegrityException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "authors/form";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Author author = authorService.getAuthorById(id);
        model.addAttribute("author", author);
        return "authors/edit";
    }

    @PostMapping("/update/{id}")
    public String updateAuthor(@PathVariable Long id,
            @Valid @ModelAttribute("author") Author author,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "authors/edit";
        }
        try {
            authorService.updateAuthor(id, author);
            return "redirect:/authors";
        } catch (DataIntegrityException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "authors/edit";
        }
    }
}
