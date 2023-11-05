--liquibase formatted sql
--changeset ssagan:1

create table if not exists author (
    id bigserial PRIMARY KEY,
    name varchar NOT NULL,
    surname varchar NOT NULL
);

create table if not exists genre (
    id bigserial PRIMARY KEY,
    name varchar NOT NULL
);

create table if not exists book (
    id bigserial PRIMARY KEY,
    name varchar NOT NULL,
    genre_id bigint NOT NULL,
    FOREIGN KEY (genre_id) REFERENCES genre (id)
);

create table if not exists author_book (
    book_id bigint NOT NULL,
    author_id bigint NOT NULL,
    FOREIGN KEY (book_id) REFERENCES book (id),
    FOREIGN KEY (author_id) REFERENCES author (id)
);

insert into author(name, surname) values
('Александр', 'Пушкин'),
('Николай', 'Гоголь'),
('Лев', 'Толстой'),
('Михаил', 'Булгаков'),
('Федор', 'Достоевский');

insert into genre(name) values
('Рассказ'),
('Роман'),
('Комедия'),
('Драма');

insert into book(name, genre_id) values
('Война и мир', 2),
('Преступление и наказание', 2),
('Нос', 1),
('Мастер и Маргарита', 2);

insert into author_book(book_id, author_id) values
(1, 3),
(2, 5),
(3, 2),
(4, 4);