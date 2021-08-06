package com.luxoft.library.controllers;

import com.luxoft.library.controllers.testdata.BookData;
import com.luxoft.library.repository.BaseAuthorRepository;
import com.luxoft.library.repository.BaseBookRepository;
import com.luxoft.library.repository.jpa.JpaGenresRepository;
import com.luxoft.library.service.BookService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// example webmvc test with partial spring boot Component Scan
@WebMvcTest(BookController.class)
@Import(BookService.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BaseBookRepository bookRepo;

    @MockBean
    private BaseAuthorRepository authorRepo;

    @MockBean
    private JpaGenresRepository genreRepo;

    @Test
    void getAll() throws Exception {
        when(bookRepo.findAll()).thenReturn(BookData.prepareListOfBooks());

        this.mockMvc.perform(get("/books"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("name1")))
                .andExpect(content().string(containsString("name2")))
                .andExpect(content().string(containsString("name3")));
    }
}