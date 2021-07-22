package com.luxoft.library.service;

import com.luxoft.library.model.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
class AuthorServiceTest {

    @Autowired
    private AuthorService authorService;

    @Test
    void getAll() {
        List<Author> list = authorService.getAll();
        assertEquals(2, list.size());
    }

    @Test
    void getById() {
        int id = 1;
        Optional<Author> optAuthor = authorService.getById(id);
        Author author = optAuthor.orElse(null);
        assertThat(author.toString()).contains("Senior engineer with Pivotal");
    }

    @Test
    void deleteById() {
        int id = 1;
        boolean deleted = authorService.deleteById(id);
        assertTrue(deleted);
    }

    @Test
    void create() {
        Author author = new Author("sampleName", "sampleSurname", "SomeInfo");
        authorService.create(author);
        List<Author> list = authorService.getAll();
        assertEquals(3, list.size());
    }

    @Test
    void update() {
        int id = 1;
        Author authorToUpdate = new Author("updatedName", "updatedSurname", "SomeInfo");
        authorToUpdate.setId(id);

        authorService.update(id, authorToUpdate);
        Optional<Author> authorFromDb = authorService.getById(id);
        assertEquals(authorToUpdate, authorFromDb.orElse(null));
    }
}

