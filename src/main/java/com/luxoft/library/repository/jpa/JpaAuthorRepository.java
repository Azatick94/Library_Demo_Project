package com.luxoft.library.repository.jpa;

import com.luxoft.library.model.Author;
import com.luxoft.library.repository.BaseRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public class JpaAuthorRepository implements BaseRepository<Author> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Author> findAll() {
        return em.createQuery("SELECT a FROM Author a JOIN FETCH a.books", Author.class)
                .getResultList();
    }

    @Override
    public Optional<Author> findById(int id) {
        return Optional.ofNullable(em.createQuery("SELECT a FROM Author a JOIN FETCH a.books WHERE a.id=:id", Author.class)
                .setParameter("id", id).getSingleResult());
    }

    @Override
    @Transactional
    public boolean deleteById(int id) {
        return em.createQuery("DELETE FROM Author a WHERE a.id=:id")
                .setParameter("id", id)
                .executeUpdate() != 0;
    }

    @Override
    @Transactional
    public void save(Author author) {

        if (author.isNew()) {
            em.persist(author);
        } else {
            em.merge(author);
        }
        em.flush();
    }
}