package com.luxoft.library.controllers;

import com.luxoft.library.dto.AuthorTo;
import com.luxoft.library.dto.mappings.AuthorMappingUtil;
import com.luxoft.library.model.Author;
import com.luxoft.library.service.AuthorService;
import com.luxoft.library.utils.logging.AspectLogger;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Tag(name = "Authors", description = "Authors API")
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;
    private final AuthorMappingUtil authorMappingUtil;

    public AuthorController(AuthorService authorService, AuthorMappingUtil authorMappingUtil) {
        this.authorService = authorService;
        this.authorMappingUtil = authorMappingUtil;
    }

    @GetMapping
    @AspectLogger
    @ResponseStatus(HttpStatus.OK)
    public List<AuthorTo> getAll() {
        List<Author> authors = authorService.getAll();
        return authors.stream().map(authorMappingUtil::getAuthorTo).collect(Collectors.toList());
    }

    @GetMapping("{id}")
    @AspectLogger
    public ResponseEntity<AuthorTo> getById(@PathVariable Integer id) {
        Author author = authorService.getById(id);
        AuthorTo authorTo = authorMappingUtil.getAuthorTo(author);
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