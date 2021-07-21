package com.luxoft.library.controllers.testdata;

import com.luxoft.library.model.Book;
import com.luxoft.library.model.Genre;

import java.util.ArrayList;
import java.util.List;

public class BookData {

    public static Book bookId1 = new Book("BookToDelete", new Genre("genre"));
    public static Book bookId2 = new Book("BookToDelete", new Genre("genre"));
    public static Book bookId3 = new Book("BookNotUpdated", new Genre("genre"));
    public static Book bookId4 = new Book("BookNotUpdated", new Genre("genre"));

    static {
        bookId1.setId(1);
        bookId2.setId(2);
        bookId3.setId(3);
        bookId4.setId(4);
    }

    public static List<Book> prepareListOfBooks() {
        List<Book> lstOfBooks = new ArrayList<>();
        lstOfBooks.add(new Book("name1", new Genre("sampleGenre")));
        lstOfBooks.add(new Book("name2", new Genre("sampleGenre")));
        lstOfBooks.add(new Book("name3", new Genre("sampleGenre")));
        return lstOfBooks;
    }


}
