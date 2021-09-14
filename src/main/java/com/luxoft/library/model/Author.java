package com.luxoft.library.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "authors", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "surname"}, name = "unique_author_name_surname")})
@Data
@ToString(exclude = {"books"})
@NoArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    private String name;
    private String surname;
    @Column(length = 1000)
    private String info;

    // mappedBy - name of parameter from Author class
    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)
    // https://stackoverflow.com/questions/55838173/manytomany-relationship-leads-to-stackoverflow-error
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Book> books = new ArrayList<>();

    public Author(String name, String surname, String info) {
        this.name = name;
        this.surname = surname;
        this.info = info;
    }

    public boolean isNew() {
        return getId() == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return Objects.equals(id, author.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
