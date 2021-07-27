package com.luxoft.library.service;

import com.luxoft.library.model.Author;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Sql(scripts = "classpath:db/data.sql")
// before each method returns initial state of Db
@Transactional
class AuthorServiceTest {

    @Autowired
    private AuthorService authorService;

    @DisplayName("getAll method successful - (returns list of 2 elements)")
    @Test
    void shouldReturnAuthorList() {
        List<Author> list = authorService.getAll();
        assertEquals(2, list.size());
    }

    @DisplayName("getById method successful - (returns author)")
    @Test
    void shouldReturnExistingAuthor() {
        int id = 1;
        Optional<Author> optAuthor = authorService.getById(id);

        assertTrue(optAuthor.isPresent());
        optAuthor.ifPresent(author -> assertThat(author.toString()).contains("Senior engineer with Pivotal"));
    }

    @DisplayName("getById method successful - (returns nothing when Bad id)")
    @Test
    void shouldNotFindNotExistingAuthor() {
        int id = 999;
        Optional<Author> optAuthor = authorService.getById(id);
        assertFalse(optAuthor.isPresent());
    }

    @DisplayName("deleteById method successful - (returns nothing when Deleted)")
    @Test
    void shouldDeleteExistingAuthor() {
        int id = 1;
        authorService.deleteById(id);
        assertFalse(authorService.getById(id).isPresent());
    }

    @DisplayName("deleteById method successful - (when deleting non existing should do nothing)")
    @Test
    void shouldNotDeleteAnything() {
        int id = 999;
        authorService.deleteById(id);
        List<Author> list = authorService.getAll();
        assertEquals(2, list.size());
    }

    @DisplayName("create method successful - (when creating new author)")
    @Test
    void shouldCreateNewAuthor() {
        Author author = new Author("sampleName", "sampleSurname", "SomeInfo");
        authorService.create(author);
        List<Author> list = authorService.getAll();
        assertEquals(3, list.size());
    }

    @DisplayName("update method successful - (when updating existing author)")
    @Test
    void shouldUpdateExistingAuthor() {
        int id = 1;
        Author authorToUpdate = new Author("updatedName", "updatedSurname", "SomeInfo");
        authorToUpdate.setId(id);

        authorService.update(id, authorToUpdate);
        Optional<Author> authorFromDb = authorService.getById(id);

        assertTrue(authorFromDb.isPresent());
        authorFromDb.ifPresent(author -> assertThat(author.toString()).contains("updatedName"));
    }

    @DisplayName("update method successful - (when updating non existing author)")
    @Test
    void shouldNotUpdateOrCreateAuthorWithNonExistingId() {
        int id = 999;
        Author authorToUpdate = new Author("updatedName", "updatedSurname", "SomeInfo");
        authorToUpdate.setId(id);

        authorService.update(id, authorToUpdate);
        Optional<Author> authorFromDb = authorService.getById(id);

        assertFalse(authorFromDb.isPresent());
    }
}

