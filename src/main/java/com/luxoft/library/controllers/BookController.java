package com.luxoft.library.controllers;

import com.luxoft.library.model.Book;
import com.luxoft.library.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.luxoft.library.utils.MainUtil.processServiceStatus;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Book> getAll() {
        return bookService.getAll();
    }

    @GetMapping("{id}")
    public ResponseEntity<Book> getById(@PathVariable Integer id) {
        Optional<Book> book = bookService.getById(id);
        return book.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Integer id) {
        boolean status = bookService.deleteById(id);
        return processServiceStatus(status);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Book book) {
        bookService.create(book);
    }

    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> update(@PathVariable Integer id, @RequestBody Book book) {
        boolean status = bookService.update(id, book);
        return processServiceStatus(status);
    }

    @PutMapping(value = "/add_author")
    public ResponseEntity<HttpStatus> addAuthor(@RequestParam("book_id") Integer bookId, @RequestParam("author_id") Integer authorId) {
        // book_id, author_id connection
        boolean status = bookService.addAuthor(bookId, authorId);
        return processServiceStatus(status);
    }

    @PutMapping(value = "/remove_author")
    public ResponseEntity<HttpStatus> removeAuthor(@RequestParam("book_id") Integer bookId, @RequestParam("author_id") Integer authorId) {
        boolean status = bookService.removeAuthor(bookId, authorId);
        return processServiceStatus(status);
    }
}
