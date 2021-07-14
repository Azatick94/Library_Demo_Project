package com.luxoft.library.controllers;

import com.luxoft.library.model.Author;
import com.luxoft.library.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    @Autowired
    private AuthorRepository authorRepo;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Author> getAll() {
        return authorRepo.findAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Author> getById(@PathVariable Integer id) {
        Optional<Author> author = authorRepo.findById(id);
        return author.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable Integer id) {
        authorRepo.delete(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void save(@RequestBody Author author) {
        authorRepo.save(author);
    }

    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> update(@PathVariable Integer id, @RequestBody Author author) {

        boolean b = authorRepo.findById(id).isPresent();
        if (b) {
            Author newAuthor = new Author();
            newAuthor.setId(id);
            newAuthor.setName(author.getName());
            newAuthor.setSurname(author.getSurname());
            newAuthor.setInfo(author.getInfo());
            authorRepo.save(newAuthor);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
