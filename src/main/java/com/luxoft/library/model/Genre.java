package com.luxoft.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "genres", uniqueConstraints = {@UniqueConstraint(columnNames = {"value"}, name = "unique_genre")})
@Getter
@Setter
@NoArgsConstructor
//@Data
//@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true, exclude = {"books"})
public class Genre extends AbstractBaseEntity {

    private String value;

    @JsonIgnore
    @OneToMany(mappedBy = "genre", cascade = CascadeType.ALL)
    private List<Book> books;

    public Genre(String value) {
        this.value = value;
    }
}

