package com.luxoft.library.repository;

import com.luxoft.library.model.BookAuthors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface BookAuthorsRepository extends JpaRepository<BookAuthors, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM BookAuthors ba WHERE ba.id=:id")
    void delete(@Param("id") int id);

    @Query("SELECT ba FROM BookAuthors ba WHERE ba.authorId=:author_id AND ba.bookId=:book_id")
    Optional<BookAuthors> findByAuthorIdAndBookId(@Param("author_id") int authorId, @Param("book_id") int bookId);
}
