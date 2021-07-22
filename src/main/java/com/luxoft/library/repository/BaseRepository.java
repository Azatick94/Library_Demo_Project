package com.luxoft.library.repository;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<T> {

    List<T> findAll();

    Optional<T> findById(int id);

    boolean deleteById(int id);

    void save(T t);
}
