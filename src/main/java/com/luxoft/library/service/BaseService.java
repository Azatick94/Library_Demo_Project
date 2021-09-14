package com.luxoft.library.service;

import java.util.List;

public interface BaseService<T> {

    List<T> getAll();

    T getById(Integer id);

    void deleteById(Integer id);

    void create(T t);

    void update(Integer id, T t);
}
