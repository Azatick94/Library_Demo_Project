package com.luxoft.library.repository.datajpa.crud;

import com.luxoft.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrudBookRepository extends JpaRepository<Book, Integer> {
}
