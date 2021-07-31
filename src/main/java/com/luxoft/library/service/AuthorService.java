package com.luxoft.library.service;

import com.luxoft.library.model.Author;
import com.luxoft.library.repository.BaseAuthorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.luxoft.library.utils.MainUtil.processOptional;

@Service
public class AuthorService implements BaseService<Author> {

    private final BaseAuthorRepository authorRepo;

    public AuthorService(BaseAuthorRepository authorRepo) {
        this.authorRepo = authorRepo;
    }

    @Override
    public List<Author> getAll() {
        return authorRepo.findAll();
    }

    @Override
    public Author getById(Integer id) {
        Optional<Author> author = authorRepo.findById(id);
        return processOptional(author, id);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        Optional<Author> author = authorRepo.findById(id);
        processOptional(author, id);
        authorRepo.deleteById(id);
    }

    @Override
    @Transactional
    public void create(Author author) {
        authorRepo.save(author);
    }

    @Override
    @Transactional
    public void update(Integer id, Author author) {
        Optional<Author> authorOptional = authorRepo.findById(id);
        Author currentAuthor = processOptional(authorOptional, id);

        currentAuthor.setName(author.getName());
        currentAuthor.setSurname(author.getSurname());
        currentAuthor.setInfo(author.getInfo());
        authorRepo.save(currentAuthor);
    }
}
