DELETE
FROM book_authors;
DELETE
FROM authors;
DELETE
FROM books;
DELETE
FROM genres;

INSERT INTO genres (value)
VALUES ('ADVENTURE'),
       ('COMIC'),
       ('ROMANCE'),
       ('HORROR'),
       ('HISTORY'),
       ('OTHER');

-- inserting data to authors
INSERT INTO authors (name, surname, info)
VALUES ('Craig', 'Walls', 'Senior engineer with Pivotal as the Spring Social project lead'),
       ('Joshua', 'Bloch',
        'American software engineer and a technology author, formerly employed at Sun Microsystems and Google');

-- inserting data to books
INSERT INTO books (name, genre_id)
VALUES ('Spring in Action', 1),
       ('Effective Java', 2);

-- inserting data to book_authors joint table
INSERT INTO book_authors (book_id, author_id)
VALUES (1, 1),
       (2, 2);
