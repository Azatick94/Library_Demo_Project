package com.luxoft.library.dto;

import lombok.Data;

@Data
public class BookForAuthorTo {

    private String name;
    private String genre;

    public BookForAuthorTo(String name, String genre) {
        this.name = name;
        this.genre = genre;
    }
}
