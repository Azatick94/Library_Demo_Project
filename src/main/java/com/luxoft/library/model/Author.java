package com.luxoft.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "authors", uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "surname"}, name = "unique_author_name_surname")})
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class Author extends AbstractBaseEntity {

    private String name;
    private String surname;
    @Column(length = 1000)
    private String info;

    // mappedBy - name of parameter from Author class
    @ManyToMany(mappedBy = "authors", fetch = FetchType.LAZY)
    // https://stackoverflow.com/questions/55838173/manytomany-relationship-leads-to-stackoverflow-error
    @JsonIgnore
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Book> books = new HashSet<>();
}
