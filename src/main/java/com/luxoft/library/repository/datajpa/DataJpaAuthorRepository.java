package com.luxoft.library.repository.datajpa;

import com.luxoft.library.model.Author;
import com.luxoft.library.repository.BaseAuthorRepository;
import com.luxoft.library.repository.datajpa.crud.CrudAuthorRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Profile(value = "datajpa")
@Repository
public class DataJpaAuthorRepository implements BaseAuthorRepository {

    private final CrudAuthorRepository crudAuthorRepository;

    public DataJpaAuthorRepository(CrudAuthorRepository crudAuthorRepository) {
        this.crudAuthorRepository = crudAuthorRepository;
    }

    @Override
    public List<Author> findAll() {
        return crudAuthorRepository.findAll();
    }

    @Override
    public Optional<Author> findById(int id) {
        return crudAuthorRepository.findById(id);
    }

    @Override
    public boolean deleteById(int id) {
        crudAuthorRepository.deleteById(id);
        return true;
    }

    @Override
    public void save(Author author) {
        crudAuthorRepository.save(author);
    }
}
