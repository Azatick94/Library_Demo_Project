package com.luxoft.library.repository.jpa;

import com.luxoft.library.model.Author;
import com.luxoft.library.repository.BaseAuthorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ComponentScan(basePackages = "com.luxoft.library.repository.jpa")
@Import(JpaAuthorRepository.class)
@Sql(scripts = "classpath:db/data.sql")
@Transactional
@DisplayName("AuthorRepository")
class BaseAuthorRepositoryTest {

    @Autowired
    private BaseAuthorRepository authorRepo;

    @Test
    void shouldReturnAuthorList() {
        List<Author> authors = authorRepo.findAll();
        assertEquals(2, authors.size());
    }

    @Test
    void shouldReturnExistingAuthor() {
        int id = 1;
        Optional<Author> optAuthor = authorRepo.findById(id);

        assertTrue(optAuthor.isPresent());
        optAuthor.ifPresent(author -> assertThat(author.toString()).contains("Senior engineer with Pivotal"));
    }

    @Test
    void shouldNotFindNotExistingAuthor() {
        int id = 999;
        Optional<Author> optAuthor = authorRepo.findById(id);
        assertFalse(optAuthor.isPresent());
    }

    @Test
    void shouldDeleteExistingAuthor() {
        int id = 1;
        authorRepo.deleteById(id);
        assertFalse(authorRepo.findById(id).isPresent());
    }

    @Test
    void shouldCreateNewAuthor() {
        Author author = new Author("sampleName", "sampleSurname", "SomeInfo");
        authorRepo.save(author);
        List<Author> list = authorRepo.findAll();
        assertEquals(3, list.size());
    }

    @Test
    void shouldUpdateExistingAuthor() {
        int id = 1;
        Author authorToUpdate = new Author("updatedName", "updatedSurname", "SomeInfo");
        authorToUpdate.setId(id);

        authorRepo.save(authorToUpdate);
        Optional<Author> authorFromDb = authorRepo.findById(id);

        assertTrue(authorFromDb.isPresent());
        authorFromDb.ifPresent(author -> assertThat(author.toString()).contains("updatedName"));
    }
}