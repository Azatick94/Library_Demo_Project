package com.luxoft.library.repository.datajpa.crud;

import com.luxoft.library.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrudAuthorRepository extends JpaRepository<Author, Integer> {
}
