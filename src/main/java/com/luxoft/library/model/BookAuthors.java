package com.luxoft.library.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "book_authors", uniqueConstraints = {@UniqueConstraint(columnNames = {"book_id", "author_id"},
        name = "unique_book_id_author_id")})
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
public class BookAuthors extends AbstractBaseEntity {

    @Column(name = "book_id")
    private Integer bookId;

    @Column(name = "author_id")
    private Integer authorId;

    public BookAuthors(Integer bookId, Integer authorId) {
        this.bookId = bookId;
        this.authorId = authorId;
    }
}
