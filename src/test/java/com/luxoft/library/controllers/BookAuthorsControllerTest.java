package com.luxoft.library.controllers;

import com.luxoft.library.LibraryApplication;
import com.luxoft.library.model.Author;
import com.luxoft.library.model.Book;
import com.luxoft.library.model.BookAuthors;
import com.luxoft.library.model.Genre;
import com.luxoft.library.repository.AuthorRepository;
import com.luxoft.library.repository.BookAuthorsRepository;
import com.luxoft.library.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.DirtiesContext;

import javax.transaction.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(classes = LibraryApplication.class)
@DirtiesContext
// https://stackoverflow.com/questions/19813492/getting-lazyinitializationexception-on-junit-test-case
@Transactional()
@DisplayName("BookAuthorsController")
public class BookAuthorsControllerTest {

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private AuthorRepository authorRepo;

    @Autowired
    private BookAuthorsRepository bookAuthorsRepo;

    @BeforeEach
    void setUp() {
        // adding TestBook to BookRepo
        bookRepo.deleteAll();
        authorRepo.deleteAll();
        bookAuthorsRepo.deleteAll();
    }

    @DisplayName("book_id+author_id uniqueness works successfully")
    @Test
    void shouldNotAddRecordWithSameBookIdAndAuthorId() {
        bookRepo.save(new Book("Book1", Genre.OTHER));
        authorRepo.save(new Author("Author1", "Surname", "Info"));

        Integer bookId = bookRepo.findByName("Book1").get().getId();
        Integer authorId = authorRepo
                .findByNameSurnameInfo("Author1", "Surname", "Info").get().getId();

        // https://stackoverflow.com/questions/41362841/spring-data-cannot-catch-dataintegrityviolationexception-in-service-layer
        bookAuthorsRepo.saveAndFlush(new BookAuthors(bookId, authorId));
        // trying to save the same again
        assertThrows(DataIntegrityViolationException.class, () -> {
            bookAuthorsRepo.saveAndFlush(new BookAuthors(bookId, authorId));
        });
    }

    @DisplayName("mappings should be saved into BookAuthors Joint Table")
    @Test
    void shouldSaveDataToBookAuthorsJointTable() {
        bookRepo.save(new Book("Book1", Genre.OTHER));
        bookRepo.save(new Book("Book2", Genre.OTHER));
        authorRepo.save(new Author("Author1", "Surname", "Info"));

        Integer bookId1 = bookRepo.findByName("Book1").get().getId();
        Integer bookId2 = bookRepo.findByName("Book2").get().getId();
        Integer authorId1 = authorRepo
                .findByNameSurnameInfo("Author1", "Surname", "Info").get().getId();

        bookAuthorsRepo.save(new BookAuthors(bookId1, authorId1));
        bookAuthorsRepo.save(new BookAuthors(bookId2, authorId1));

        assertEquals(2, bookAuthorsRepo.findAll().size());
    }

    @Disabled
    @DisplayName("book can have several authors works successfully")
    @Test
    void shouldHavePossibilityToHaveSeveralAuthorInBook() {
        // TODO

        // books can have several authors
        bookRepo.save(new Book("Book1", Genre.OTHER));
        authorRepo.save(new Author("Author1", "Surname", "Info"));
        authorRepo.save(new Author("Author2", "Surname", "Info"));

        Integer bookId = bookRepo.findByName("Book1").get().getId();
        Integer authorId1 = authorRepo
                .findByNameSurnameInfo("Author1", "Surname", "Info").get().getId();
        Integer authorId2 = authorRepo
                .findByNameSurnameInfo("Author2", "Surname", "Info").get().getId();

        bookAuthorsRepo.save(new BookAuthors(bookId, authorId1));
        bookAuthorsRepo.save(new BookAuthors(bookId, authorId2));

        List<Book> books = bookRepo.findAll();
        assertEquals(2, books.get(0).getAuthors().size());
    }

    @Disabled
    @DisplayName("authors can be authors of several books works successfully")
    @Test
    void shouldHavePossibilityToHaveAuthorWithSeveralBooks() {
        // TODO

        // authors can be writer of several books
        bookRepo.save(new Book("Book1", Genre.OTHER));
        bookRepo.save(new Book("Book2", Genre.OTHER));
        authorRepo.save(new Author("Author1", "Surname", "Info"));

        Integer bookId1 = bookRepo.findByName("Book1").get().getId();
        Integer bookId2 = bookRepo.findByName("Book2").get().getId();
        Integer authorId1 = authorRepo
                .findByNameSurnameInfo("Author1", "Surname", "Info").get().getId();

        bookAuthorsRepo.save(new BookAuthors(bookId1, authorId1));
        bookAuthorsRepo.save(new BookAuthors(bookId2, authorId1));

        List<Author> authors = authorRepo.findAll();
        assertEquals(2, authors.get(0).getBooks().size());
    }
}