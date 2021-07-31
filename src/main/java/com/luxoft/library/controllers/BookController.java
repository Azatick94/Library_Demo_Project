package com.luxoft.library.controllers;

import com.luxoft.library.dto.BookTo;
import com.luxoft.library.model.Book;
import com.luxoft.library.service.BookService;
import com.luxoft.library.utils.MainUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static com.luxoft.library.utils.MainUtil.getBookTo;
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
    public List<BookTo> getAll() {
        List<Book> books = bookService.getAll();
        return books.stream().map(MainUtil::getBookTo).collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<BookTo> getById(@PathVariable Integer id) {
        Book book = bookService.getById(id);
        BookTo bookTo = getBookTo(book);
        return new ResponseEntity<>(bookTo, HttpStatus.OK);
    }

    @GetMapping("/by_author/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<BookTo> getByAuthorId(@PathVariable("id") Integer authorId) {
        List<Book> filteredBooks = bookService.getByAuthorId(authorId);
        return filteredBooks.stream().map(MainUtil::getBookTo).collect(Collectors.toList());
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<HttpStatus> deleteById(@PathVariable Integer id) {
        bookService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody Book book) {
        bookService.create(book);
    }

    @PutMapping(value = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<HttpStatus> update(@PathVariable Integer id, @RequestBody Book book) {
        bookService.update(id, book);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(value = "/add_author")
    public ResponseEntity<HttpStatus> addAuthor(@RequestParam("book_id") Integer bookId, @RequestParam("author_id") Integer authorId) {
        boolean status = bookService.addAuthor(bookId, authorId);
        return processServiceStatus(status);
    }

    @PutMapping(value = "/remove_author")
    public ResponseEntity<HttpStatus> removeAuthor(@RequestParam("book_id") Integer bookId, @RequestParam("author_id") Integer authorId) {
        boolean status = bookService.removeAuthor(bookId, authorId);
        return processServiceStatus(status);
    }
}
