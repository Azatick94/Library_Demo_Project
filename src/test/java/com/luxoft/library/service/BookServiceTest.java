package com.luxoft.library.service;

import com.luxoft.library.controllers.testdata.BookData;
import com.luxoft.library.model.Book;
import com.luxoft.library.model.Genre;
import com.luxoft.library.repository.BaseBookRepository;
import com.luxoft.library.repository.jpa.JpaGenresRepository;
import com.luxoft.library.utils.exceptions.NotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.luxoft.library.controllers.testdata.BookData.prepareListOfBooks;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("BookService")
class BookServiceTest {

    @Mock
    private BaseBookRepository bookRepo;
    @Mock
    private JpaGenresRepository genresRepo;


    @InjectMocks
    private BookService bookService;

    @DisplayName("getAll method successful")
    @Test
    void shouldReturnBookList() {
        List<Book> lst = prepareListOfBooks();
        when(bookRepo.findAll()).thenReturn(lst);

        assertEquals(3, bookService.getAll().size());
        verify(bookRepo, times(1)).findAll();
    }

    @DisplayName("save method successful")
    @Test
    void shouldSaveBook() {
        Book book = BookData.bookId1;

        // prepare genredata
        List<Genre> genre = new ArrayList<>();
        genre.add(new Genre(book.getGenre().getId(), "ADVENTURE"));

        when(genresRepo.findAll()).thenReturn(genre);

        bookService.create(book);
        verify(bookRepo, times(1)).save(book);
    }

    @DisplayName("delete method successful when Book Exists")
    @Test
    void shouldDeleteBook() {
        int id = 1;
        Book book = BookData.bookId1;

        when(bookRepo.findById(id)).thenReturn(Optional.of(book));

        // action
        bookService.deleteById(id);
        verify(bookRepo, times(1)).deleteById(id);
    }

    @DisplayName("delete method successful when Book Does not Exist")
    @Test
    void shouldNotDeleteBook() {
        int id = 2;
        when(bookRepo.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            bookService.deleteById(id);
        });
        verify(bookRepo, never()).deleteById(id);
    }

    @DisplayName("update method successful when Existing Id")
    @Test
    void shouldUpdateExistingBook() {
        // verify when updating existing Book, should call bookRepo.save
        int id = 3;

        Book book = BookData.bookId3;
        Book newBook = new Book("BookUpdated", new Genre(1));
        newBook.setId(id);

        // when querying by id, should return book
        when(bookRepo.findById(id)).thenReturn(Optional.of(book));

        // prepare genredata
        List<Genre> genre = new ArrayList<>();
        genre.add(new Genre(1, "ADVENTURE"));

        when(genresRepo.findAll()).thenReturn(genre);

        bookService.update(id, newBook);
        verify(bookRepo, times(1)).save(book);
    }

    @DisplayName("update method successful when NonExisting Id")
    @Test
    void shouldNotUpdateBook() {
        // verify when updating existing Book, should call bookRepo.save
        int id = 4;

        Book book = BookData.bookId4;
        Book newBook = new Book("BookUpdated", new Genre("genre"));
        newBook.setId(id);

        // when querying by id, should return book
        when(bookRepo.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> {
            bookService.update(id, newBook);
        });
        verify(bookRepo, never()).save(newBook);
    }
}