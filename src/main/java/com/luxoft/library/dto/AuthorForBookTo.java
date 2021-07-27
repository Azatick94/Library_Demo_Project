package com.luxoft.library.dto;

import lombok.Data;

@Data
public class AuthorForBookTo {

    private String name;
    private String surname;
    private String info;

    public AuthorForBookTo(String name, String surname, String info) {
        this.name = name;
        this.surname = surname;
        this.info = info;
    }
}
