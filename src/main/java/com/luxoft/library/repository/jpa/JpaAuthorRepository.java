package com.luxoft.library.repository.jpa;

import com.luxoft.library.model.Author;
import com.luxoft.library.repository.BaseAuthorRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaAuthorRepository implements BaseAuthorRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Author> findAll() {
        return em.createQuery("SELECT a FROM Author a JOIN FETCH a.books", Author.class)
                .getResultList();
    }

    @Override
    public Optional<Author> findById(int id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    @Override
    public boolean deleteById(int id) {
        Optional<Author> byId = findById(id);
        em.remove(byId.get());
        return true;
    }

    @Override
    public void save(Author author) {

        if (author.isNew()) {
            em.persist(author);
        } else {
            em.merge(author);
        }
        em.flush();
    }
}