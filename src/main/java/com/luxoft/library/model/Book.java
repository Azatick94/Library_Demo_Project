package com.luxoft.library.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"}, name = "unique_book_name")})
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class Book extends AbstractBaseEntity {

    private String name;

    // specify hibernate to save enum as String
    @Enumerated(EnumType.STRING)
    private Genre genre;

    @ManyToMany(fetch = FetchType.LAZY)
    // specifying join table
    @JoinTable(
            name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Author> authors = new HashSet<>();

    public Book(String name, Genre genre) {
        this.name = name;
        this.genre = genre;
    }
}
