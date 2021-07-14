package com.luxoft.library;

import com.luxoft.library.model.Author;
import com.luxoft.library.model.Book;
import com.luxoft.library.model.Genre;
import com.luxoft.library.repository.AuthorRepository;
import com.luxoft.library.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Optional;

@SpringBootApplication
public class LibraryApplication implements CommandLineRunner {

    @Autowired
    private BookRepository bookRepo;
    @Autowired
    private AuthorRepository authorRepo;

    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        // --------------------------------------------------------------
        // SAMPLE DATA SAVING
        // books saving
        Book book1 = new Book("Spring in Action", Genre.HORROR);
        Book book2 = new Book("Effective Java", Genre.ADVENTURE);

        bookRepo.save(book1);
        bookRepo.save(book2);

        // authors saving
        Author author1 = new Author("Craig", "Walls", "Senior engineer with Pivotal as the Spring Social project lead");
        Author author2 = new Author("Joshua", "Bloch", " American software engineer and a technology author, formerly employed at Sun Microsystems and Google");
        authorRepo.save(author1);
        authorRepo.save(author2);
        // --------------------------------------------------------------
    }
}
