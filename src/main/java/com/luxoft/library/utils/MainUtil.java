package com.luxoft.library.utils;

import com.luxoft.library.dto.AuthorForBookTo;
import com.luxoft.library.dto.AuthorTo;
import com.luxoft.library.dto.BookForAuthorTo;
import com.luxoft.library.dto.BookTo;
import com.luxoft.library.model.Author;
import com.luxoft.library.model.Book;
import com.luxoft.library.utils.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class MainUtil {

    public static ResponseEntity<HttpStatus> processServiceStatus(boolean b) {
        if (b) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public static <T> T processOptional(Optional<T> t, Integer id) {
        if (t.isPresent()) {
            return t.get();
        } else {
            throw new NotFoundException("Id=" + id + " not found!!!");
        }
    }

    public static AuthorTo getAuthorTo(Author author) {
        Set<BookForAuthorTo> bookForAuthorTos = author.getBooks().stream()
                .map(MainUtil::getBookForAuthorTo).collect(Collectors.toSet());
        return new AuthorTo(author.getId(), author.getName(), author.getSurname(), author.getInfo(), bookForAuthorTos);
    }

    public static BookTo getBookTo(Book book) {
        Set<AuthorForBookTo> authorForBookTos = book.getAuthors().stream()
                .map(MainUtil::getAuthorForBookTo).collect(Collectors.toSet());
        return new BookTo(book.getId(), book.getName(), book.getGenre().getValue(), authorForBookTos);
    }

    private static BookForAuthorTo getBookForAuthorTo(Book book) {
        return new BookForAuthorTo(book.getName(), book.getGenre().getValue());
    }

    private static AuthorForBookTo getAuthorForBookTo(Author author) {
        return new AuthorForBookTo(author.getName(), author.getSurname(), author.getInfo());
    }
}
