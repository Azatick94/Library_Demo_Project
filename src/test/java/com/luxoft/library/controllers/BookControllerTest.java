package com.luxoft.library.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luxoft.library.LibraryApplication;
import com.luxoft.library.model.Author;
import com.luxoft.library.model.Book;
import com.luxoft.library.model.BookAuthors;
import com.luxoft.library.model.Genre;
import com.luxoft.library.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = LibraryApplication.class)
@DirtiesContext
@AutoConfigureMockMvc
// https://stackoverflow.com/questions/19813492/getting-lazyinitializationexception-on-junit-test-case
@Transactional
@DisplayName("BookController")
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private BookRepository bookRepo;

    private final String TEST_BOOK_NAME = "TestBook";

    @BeforeEach
    void setUp() {
        // adding TestBook to BookRepo
        bookRepo.deleteAll();
        Book book = new Book(TEST_BOOK_NAME, Genre.HORROR);
        bookRepo.save(book);
    }

    @DisplayName("getAll method successful")
    @Test
    void shouldReturnBookList() throws Exception {
        this.mockMvc.perform(get("/books"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString(TEST_BOOK_NAME)));
    }

    @DisplayName("save method successful")
    @Test
    void shouldSaveBookAndSeenInDb() throws Exception {
        this.mockMvc.perform(post("/books")
                .content(asJsonString(new Book("SavedBook", Genre.ROMANCE)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        this.mockMvc.perform(get("/books"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("SavedBook")));
    }

    @DisplayName("delete method successful")
    @Test
    void shouldDeleteBook() throws Exception {
        int id = bookRepo.findByName(TEST_BOOK_NAME).get().getId();

        // deletion
        this.mockMvc.perform(delete("/books/" + id))
                .andExpect(status().is2xxSuccessful());

        // getAll to see that was deleted
        this.mockMvc.perform(get("/books"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(not(containsString(TEST_BOOK_NAME))));
    }

    @DisplayName("update method successful - Update Book With Wrong Id")
    @Test
    void shouldThrowExceptionWhenUpdateWithWrongIdValue() throws Exception {
        this.mockMvc.perform(put("/books/999")
                .content(asJsonString(new Book("Book To Update", Genre.ROMANCE)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @DisplayName("update method successful - Update Existing Book")
    @Test
    void shouldUpdateExistingBook() throws Exception {
        int id = bookRepo.findByName(TEST_BOOK_NAME).get().getId();

        // updating the Book
        this.mockMvc.perform(put("/books/" + id)
                .content(asJsonString(new Book("Book To Update", Genre.ROMANCE)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        // checking that Book's previous name was changed to new name
        this.mockMvc.perform(get("/books"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("Book To Update")))
                .andExpect(content().string(not(containsString(TEST_BOOK_NAME))));
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}