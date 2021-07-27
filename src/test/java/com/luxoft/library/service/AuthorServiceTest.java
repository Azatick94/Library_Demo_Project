package com.luxoft.library.service;

import com.luxoft.library.model.Author;
import com.luxoft.library.repository.BaseAuthorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthorService")
class AuthorServiceTest {

    // mock repository
    @Mock
    private BaseAuthorRepository authorRepo;

    @InjectMocks
    private AuthorService authorService;

    @DisplayName("deleteById method successful - (when deleting non existing should do nothing)")
    @Test
    void shouldNotDeleteAnything() {
        int id = 999;
        authorService.deleteById(id);
        // check that repository delete method wasn't called
        verify(authorRepo, never()).deleteById(id);
    }

    @DisplayName("update method successful - (when updating non existing author)")
    @Test
    void shouldNotUpdateOrCreateAuthorWithNonExistingId() {
        int id = 999;
        Author authorToUpdate = new Author("updatedName", "updatedSurname", "SomeInfo");
        authorToUpdate.setId(id);
        authorService.update(id, authorToUpdate);
        // check that repository save method wasn't called
        verify(authorRepo, never()).save(authorToUpdate);
    }
}

