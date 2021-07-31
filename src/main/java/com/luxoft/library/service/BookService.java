package com.luxoft.library.service;

import com.luxoft.library.model.Author;
import com.luxoft.library.model.Book;
import com.luxoft.library.model.Genre;
import com.luxoft.library.repository.BaseAuthorRepository;
import com.luxoft.library.repository.BaseBookRepository;
import com.luxoft.library.repository.jpa.JpaGenresRepository;
import com.luxoft.library.utils.exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.luxoft.library.utils.MainUtil.processOptional;

@Service
public class BookService implements BaseService<Book> {

    private final BaseBookRepository bookRepo;
    private final BaseAuthorRepository authorRepo;
    private final JpaGenresRepository genresRepo;

    public BookService(BaseBookRepository bookRepo, BaseAuthorRepository authorRepo, JpaGenresRepository genresRepo) {
        this.bookRepo = bookRepo;
        this.authorRepo = authorRepo;
        this.genresRepo = genresRepo;
    }

    @Override
    public List<Book> getAll() {
        return bookRepo.findAll();
    }

    @Override
    public Book getById(Integer id) {
        Optional<Book> book = bookRepo.findById(id);
        return processOptional(book, id);
    }

    public List<Book> getByAuthorId(Integer authorId) {
        return bookRepo.findByAuthorId(authorId);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        Optional<Book> book = bookRepo.findById(id);
        processOptional(book, id);
        bookRepo.deleteById(id);
    }

    @Override
    @Transactional
    public void create(Book book) {
        Integer genreId = book.getGenre().getId();
        if (checkGenrePresence(genreId)) {
            bookRepo.save(book);
        } else {
            throw new NotFoundException("Genre Id=" + genreId + " not found!!!");
        }
    }

    @Override
    @Transactional
    public void update(Integer id, Book book) {
        Optional<Book> bookOptional = bookRepo.findById(id);
        Book currentBook = processOptional(bookOptional, id);

        Integer genreId = book.getGenre().getId();
        if (checkGenrePresence(genreId)) {
            currentBook.setName(book.getName());
            currentBook.setGenre(book.getGenre());
            bookRepo.save(currentBook);
        } else {
            throw new NotFoundException("Genre Id=" + genreId + " not found!!!");
        }
    }

    @Transactional
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

    @Transactional
    public boolean removeAuthor(Integer bookId, Integer authorId) {
        Optional<Book> book = bookRepo.findById(bookId);
        boolean cond = book.isPresent();
        if (cond) {
            book.get().getAuthors().removeIf(a -> a.getId().equals(authorId));
            bookRepo.save(book.get());
        }
        return cond;
    }

    private boolean checkGenrePresence(Integer genreId) {
        List<Genre> genreList = genresRepo.findAll();
        List<Integer> genreIds = genreList.stream().map(Genre::getId).collect(Collectors.toList());
        return genreIds.contains(genreId);
    }

}
