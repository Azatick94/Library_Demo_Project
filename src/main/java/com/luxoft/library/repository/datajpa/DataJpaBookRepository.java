package com.luxoft.library.repository.datajpa;

import com.luxoft.library.model.Book;
import com.luxoft.library.repository.BaseBookRepository;
import com.luxoft.library.repository.datajpa.crud.CrudBookRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Profile(value = "datajpa")
@Repository
public class DataJpaBookRepository implements BaseBookRepository {

    private final CrudBookRepository crudBookRepository;

    public DataJpaBookRepository(CrudBookRepository crudBookRepository) {
        this.crudBookRepository = crudBookRepository;
    }

    @Override
    public List<Book> findAll() {
        return crudBookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(int id) {
        return crudBookRepository.findById(id);
    }

    @Override
    public boolean deleteById(int id) {
        crudBookRepository.deleteById(id);
        return true;
    }

    @Override
    public void save(Book book) {
        crudBookRepository.save(book);
    }
}
