package com.luxoft.library.repository.jpa;

import com.luxoft.library.model.Book;
import com.luxoft.library.repository.BaseBookRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaBookRepository implements BaseBookRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Book> findAll() {
        return em.createQuery("SELECT b FROM Book b", Book.class)
                .getResultList();
    }

    @Override
    public Optional<Book> findById(int id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    @Override
    public List<Book> findByAuthorId(Integer authorId) {
        List<Book> booksFiltered = new ArrayList<>();
        for (Book book : findAll()) {
            boolean anyMatch = book.getAuthors().stream().anyMatch(a -> a.getId().equals(authorId));
            if (anyMatch) {
                booksFiltered.add(book);
            }
        }
        return booksFiltered;
    }

    @Override
    public void deleteById(int id) {
        Optional<Book> byId = findById(id);
        em.remove(byId.orElse(null));
    }

    @Override
    public void save(Book book) {
        if (book.isNew()) {
            em.persist(book);
        } else {
            em.merge(book);
        }
    }
}
