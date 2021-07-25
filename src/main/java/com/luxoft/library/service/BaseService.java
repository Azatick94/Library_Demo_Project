package com.luxoft.library.service;

import java.util.List;
import java.util.Optional;

public interface BaseService<T> {

    List<T> getAll();

    Optional<T> getById(Integer id);

    boolean deleteById(Integer id);

    void create(T t);

    boolean update(Integer id, T t);
}
