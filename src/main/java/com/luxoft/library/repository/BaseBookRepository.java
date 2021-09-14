package com.luxoft.library.repository;

import com.luxoft.library.model.Book;

import java.util.List;

public interface BaseBookRepository extends BaseRepository<Book> {

    List<Book> findByAuthorId(Integer authorId);
}
