package com.luxoft.library.dto;

import lombok.Data;
import java.util.Set;

@Data
public class BookTo {

    private Integer id;
    private String name;
    private String genre;
    private Set<AuthorForBookTo> authors;

    public BookTo(Integer id, String name, String genre, Set<AuthorForBookTo> authors) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.authors = authors;
    }
}
