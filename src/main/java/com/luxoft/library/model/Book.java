package com.luxoft.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books", uniqueConstraints = {@UniqueConstraint(columnNames = {"name"}, name = "unique_book_name")})
@Data
//@Getter
//@Setter
//@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = {"authors"})
public class Book  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    private String name;

    @ManyToOne() //  fetch = FetchType.LAZY
    // join column genre_id
    @JoinColumn(name = "genre_id")
    private Genre genre;

    @ManyToMany(fetch = FetchType.LAZY)
    // specifying join table
    @JoinTable(
            name = "book_authors",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Set<Author> authors = new HashSet<>();

    public Book(String name, Genre genre) {
        this.name = name;
        this.genre = genre;
    }

    public boolean isNew() {
        return getId() == null;
    }
}
