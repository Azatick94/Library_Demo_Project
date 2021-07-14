package com.luxoft.library.controllers;

import com.luxoft.library.model.BookAuthors;
import com.luxoft.library.repository.BookAuthorsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/book_authors")
public class BookAuthorsController {

    @Autowired
    private BookAuthorsRepository bookAuthorsRepo;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BookAuthors> getAll() {
        return bookAuthorsRepo.findAll();
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public void addBook(@RequestBody BookAuthors bookAuthors) {
        bookAuthorsRepo.save(bookAuthors);
    }

    // removing book from author
    @PostMapping("/remove")
    public ResponseEntity<HttpStatus> removeBook(@RequestBody BookAuthors bookAuthors) {
        Integer authorId = bookAuthors.getAuthorId();
        Integer bookId = bookAuthors.getBookId();

        Optional<BookAuthors> bookAuthor = bookAuthorsRepo.findByAuthorIdAndBookId(authorId, bookId);
        if (bookAuthor.isPresent()) {
            bookAuthorsRepo.delete(bookAuthor.get().getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}