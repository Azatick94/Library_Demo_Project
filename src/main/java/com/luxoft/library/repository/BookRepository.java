package com.luxoft.library.repository;

import com.luxoft.library.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    @Query("SELECT DISTINCT b FROM Book b LEFT JOIN b.authors")
    List<Book> findAll();

    @Query("SELECT b FROM Book b LEFT JOIN b.authors WHERE b.id=:id")
    Optional<Book> findById(@Param("id") Integer id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Book b WHERE b.id=:id")
    void delete(@Param("id") int id);

    @Query("SELECT b FROM Book b LEFT JOIN b.authors WHERE b.name=:name")
    Optional<Book> findByName(@Param("name") String name);
}
