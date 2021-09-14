package com.luxoft.library.repository.jpa;

import com.luxoft.library.model.Author;
import com.luxoft.library.model.Book;
import com.luxoft.library.repository.BaseBookRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

import static com.luxoft.library.utils.MainUtil.processOptional;

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
        Optional<Author> authorOpt = Optional.ofNullable(em.find(Author.class, authorId));
        Author author = processOptional(authorOpt, authorId);
        return author.getBooks();
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
