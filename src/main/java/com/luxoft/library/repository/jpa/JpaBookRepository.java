package com.luxoft.library.repository.jpa;

import com.luxoft.library.model.Book;
import com.luxoft.library.repository.BaseRepository;

import java.util.List;
import java.util.Optional;

public class JpaBookRepository implements BaseRepository<Book> {
    @Override
    public List findAll() {
        return null;
    }

    @Override
    public Optional findById(int id) {
        return Optional.empty();
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }

    @Override
    public void save(Book book) {

    }

}
