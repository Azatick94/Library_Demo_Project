package com.luxoft.library.service;

import com.luxoft.library.model.Author;
import com.luxoft.library.model.Book;
import com.luxoft.library.repository.AuthorRepository;
import com.luxoft.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    private final BookRepository bookRepo;
    private final AuthorRepository authorRepo;

    public BookService(BookRepository bookRepo, AuthorRepository authorRepo) {
        this.bookRepo = bookRepo;
        this.authorRepo = authorRepo;
    }

    public List<Book> getAll() {
        return bookRepo.findAll();
    }

    public Optional<Book> getById(Integer id) {
        return bookRepo.findById(id);
    }

    public boolean deleteById(Integer id) {
        Optional<Book> book = bookRepo.findById(id);
        boolean status = book.isPresent();
        if (status) {
            bookRepo.deleteById(id);
        }
        return status;
    }

    public void create(Book book) {
        bookRepo.save(book);
    }

    public boolean update(Integer id, Book book) {
        // TODO case when book Genre Id not Valid
        boolean b = bookRepo.findById(id).isPresent();
        if (b) {
            Book currentBook = bookRepo.findById(id).get();
            currentBook.setName(book.getName());
            currentBook.setGenre(book.getGenre());
            bookRepo.save(currentBook);
        }
        return b;
    }

    public boolean addAuthor(Integer bookId, Integer authorId) {
        Optional<Book> book = bookRepo.findById(bookId);
        Optional<Author> author = authorRepo.findById(authorId);
        boolean cond = book.isPresent() && author.isPresent();
        if (cond) {
            book.get().getAuthors().add(author.get());
            bookRepo.save(book.get());
        }
        return cond;
    }

    public boolean removeAuthor(Integer bookId, Integer authorId) {
        Optional<Book> book = bookRepo.findById(bookId);
        boolean cond = book.isPresent();
        // TODO when authorId not present in book Authors list
        if (cond) {
            book.get().getAuthors().removeIf(a -> a.getId() == authorId);
            bookRepo.save(book.get());
        }
        return cond;
    }
}
