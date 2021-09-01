package com.luxoft.library.dto.mappings;

import com.luxoft.library.dto.AuthorTo;
import com.luxoft.library.dto.BookForAuthorTo;
import com.luxoft.library.model.Author;
import com.luxoft.library.model.Book;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class AuthorMappingUtil {

    public AuthorTo getAuthorTo(Author author) {
        Set<BookForAuthorTo> bookForAuthorTos = author.getBooks().stream()
                .map(this::getBookForAuthorTo).collect(Collectors.toSet());
        return new AuthorTo(author.getId(), author.getName(), author.getSurname(), author.getInfo(), bookForAuthorTos);
    }

    private BookForAuthorTo getBookForAuthorTo(Book book) {
        return new BookForAuthorTo(book.getName(), book.getGenre().getValue());
    }


}
