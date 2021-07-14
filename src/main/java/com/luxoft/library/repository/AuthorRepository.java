package com.luxoft.library.repository;

import com.luxoft.library.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {

    @Query("SELECT DISTINCT a FROM Author a LEFT JOIN a.books")
    List<Author> findAll();

    @Query("SELECT a FROM Author a LEFT JOIN a.books WHERE a.id=:id")
    Optional<Author> findById(@Param("id") Integer id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Author a WHERE a.id=:id")
    void delete(@Param("id") int id);

    @Query("SELECT a FROM Author a LEFT JOIN a.books WHERE a.name=:name AND a.surname=:surname AND a.info=:info")
    Optional<Author> findByNameSurnameInfo(@Param("name") String name, @Param("surname") String surname,
                                @Param("info") String info);
}
