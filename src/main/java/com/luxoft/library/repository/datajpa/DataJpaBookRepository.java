package com.luxoft.library.repository.datajpa;

import com.luxoft.library.model.Author;
import com.luxoft.library.model.Book;
import com.luxoft.library.repository.BaseBookRepository;
import com.luxoft.library.repository.datajpa.crud.CrudAuthorRepository;
import com.luxoft.library.repository.datajpa.crud.CrudBookRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.luxoft.library.utils.MainUtil.processOptional;

@Profile(value = "datajpa")
@Repository
public class DataJpaBookRepository implements BaseBookRepository {

    private final CrudBookRepository crudBookRepository;
    private final CrudAuthorRepository crudAuthorRepository;

    public DataJpaBookRepository(CrudBookRepository crudBookRepository, CrudAuthorRepository crudAuthorRepository) {
        this.crudBookRepository = crudBookRepository;
        this.crudAuthorRepository = crudAuthorRepository;
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
    public List<Book> findByAuthorId(Integer authorId) {
        Optional<Author> authorOpt = crudAuthorRepository.findById(authorId);
        Author author = processOptional(authorOpt, authorId);
        return author.getBooks();
    }

    @Override
    public void deleteById(int id) {
        crudBookRepository.deleteById(id);
    }

    @Override
    public void save(Book book) {
        crudBookRepository.save(book);
    }
}
