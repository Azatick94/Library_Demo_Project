package com.luxoft.library.repository.jpa;

import com.luxoft.library.model.Genre;
import com.luxoft.library.repository.BaseRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class JpaGenresRepository implements BaseRepository<Genre> {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Genre> findAll() {
        return em.createQuery("SELECT g FROM Genre g", Genre.class)
                .getResultList();
    }

    @Override
    public Optional<Genre> findById(int id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }

    @Override
    public void save(Genre genre) {

    }
}
