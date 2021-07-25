package com.luxoft.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "genres", uniqueConstraints = {@UniqueConstraint(columnNames = {"value"}, name = "unique_genre")})
@NoArgsConstructor
@Data
@ToString(exclude = {"books"})
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String value;

    @JsonIgnore
    @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL)
    private List<Book> books;

    public Genre(String value) {
        this.value = value;
    }
}
