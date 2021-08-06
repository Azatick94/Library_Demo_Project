DROP TABLE IF EXISTS book_authors CASCADE;
DROP TABLE IF EXISTS authors CASCADE;
DROP TABLE IF EXISTS books CASCADE;

-- authors table
CREATE TABLE authors
(
    id      INTEGER PRIMARY KEY AUTO_INCREMENT,
    uuid    VARCHAR,
    name    VARCHAR,
    surname VARCHAR,
    info    VARCHAR(1000)
);
CREATE UNIQUE INDEX unique_author_name_surname ON authors (name, surname);

-- Table with Genres
CREATE TABLE genres
(
    id    INTEGER PRIMARY KEY AUTO_INCREMENT,
    value VARCHAR NOT NULL
);

CREATE UNIQUE INDEX unique_genre ON genres (value);


-- books table
CREATE TABLE books
(
    id       INTEGER PRIMARY KEY AUTO_INCREMENT,
    name     VARCHAR,
    genre_id INTEGER,
    FOREIGN KEY (genre_id) REFERENCES genres (id)
);

-- Joint Table between authors and books
CREATE TABLE book_authors
(
    id        INTEGER PRIMARY KEY AUTO_INCREMENT,
    book_id   INTEGER,
    author_id INTEGER,
    FOREIGN KEY (book_id) REFERENCES books (id) ON DELETE CASCADE,
    FOREIGN KEY (author_id) REFERENCES authors (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX unique_book_id_author_id ON book_authors (book_id, author_id);


