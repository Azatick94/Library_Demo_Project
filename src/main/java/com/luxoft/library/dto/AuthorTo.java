package com.luxoft.library.dto;

import lombok.Data;
import java.util.Set;

@Data
public class AuthorTo {

    private Integer id;
    private String name;
    private String surname;
    private String info;
    private Set<BookForAuthorTo> books;

    public AuthorTo(Integer id, String name, String surname, String info, Set<BookForAuthorTo> books) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.info = info;
        this.books = books;
    }
}
