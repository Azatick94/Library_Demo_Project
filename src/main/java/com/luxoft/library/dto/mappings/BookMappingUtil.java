package com.luxoft.library.dto.mappings;

import com.luxoft.library.dto.AuthorForBookTo;
import com.luxoft.library.dto.BookTo;
import com.luxoft.library.model.Author;
import com.luxoft.library.model.Book;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BookMappingUtil {

    public BookTo getBookTo(Book book) {
        Set<AuthorForBookTo> authorForBookTos = book.getAuthors().stream()
                .map(this::getAuthorForBookTo).collect(Collectors.toSet());
        return new BookTo(book.getId(), book.getName(), book.getGenre().getValue(), authorForBookTos);
    }

    private AuthorForBookTo getAuthorForBookTo(Author author) {
        return new AuthorForBookTo(author.getName(), author.getSurname(), author.getInfo());
    }

}
