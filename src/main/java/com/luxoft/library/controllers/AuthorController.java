package com.luxoft.library.controllers;

import com.luxoft.library.model.Author;
import com.luxoft.library.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.luxoft.library.utils.MainUtil.processServiceStatus;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Author> getAll() {
        return authorService.getAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Author> getById(@PathVariable Integer id) {
        Optional<Author> author = authorService.getById(id);
        return author.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Integer id) {
        boolean status = authorService.deleteById(id);
        return processServiceStatus(status);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Author author) {
        authorService.create(author);
    }

    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> update(@PathVariable Integer id, @RequestBody Author author) {
        boolean status = authorService.update(id, author);
        return processServiceStatus(status);
    }
}