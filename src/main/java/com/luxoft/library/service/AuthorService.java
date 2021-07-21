package com.luxoft.library.service;

import com.luxoft.library.model.Author;
import com.luxoft.library.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepo;

    public AuthorService(AuthorRepository authorRepo) {
        this.authorRepo = authorRepo;
    }

    public List<Author> getAll() {
        return authorRepo.findAll();
    }

    public Optional<Author> getById(Integer id) {
        return authorRepo.findById(id);
    }

    public boolean deleteById(Integer id) {
        Optional<Author> author = authorRepo.findById(id);
        boolean status = author.isPresent();
        if (status) {
            authorRepo.deleteById(id);
        }
        return status;
    }

    public void create(Author author) {
        authorRepo.save(author);
    }

    public boolean update(Integer id, Author author) {
        boolean b = authorRepo.findById(id).isPresent();
        if (b) {
            Author currentAuthor = authorRepo.findById(id).get();
            currentAuthor.setName(author.getName());
            currentAuthor.setSurname(author.getSurname());
            currentAuthor.setInfo(author.getInfo());
            authorRepo.save(currentAuthor);
        }
        return b;
    }
}
