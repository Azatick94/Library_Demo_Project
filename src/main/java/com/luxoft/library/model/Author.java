package com.luxoft.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "authors")
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class Author extends AbstractBaseEntity {

    private String name;
    private String surname;
    @Column(length = 1000)
    private String info;

    // mappedBy - name of parameter from Author class
    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)
    // https://stackoverflow.com/questions/55838173/manytomany-relationship-leads-to-stackoverflow-error
    @JsonIgnore
    private Set<Book> books = new HashSet<>();

    public Author(String name, String surname, String info) {
        this.name = name;
        this.surname = surname;
        this.info = info;
    }
}
