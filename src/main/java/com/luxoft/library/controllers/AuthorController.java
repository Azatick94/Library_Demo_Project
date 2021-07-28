package com.luxoft.library.controllers;

import com.luxoft.library.dto.AuthorTo;
import com.luxoft.library.model.Author;
import com.luxoft.library.service.AuthorService;
import com.luxoft.library.utils.MainUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.luxoft.library.utils.MainUtil.getAuthorTo;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<AuthorTo> getAll() {
        List<Author> authors = authorService.getAll();
        return authors.stream().map(MainUtil::getAuthorTo).collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<AuthorTo> getById(@PathVariable Integer id) {
        Author author = authorService.getById(id);
        AuthorTo authorTo = getAuthorTo(author);
        return new ResponseEntity<>(authorTo, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Integer id) {
        authorService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Author author) {
        authorService.create(author);
    }

    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> update(@PathVariable Integer id, @RequestBody Author author) {
        authorService.update(id, author);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}