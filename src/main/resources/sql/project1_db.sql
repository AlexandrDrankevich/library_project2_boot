create table person
( id   integer generated by default as identity primary key,
    name  varchar(30) not null unique,
    birth_date integer  not null,
    password   varchar,
    role       varchar
);

create table book(
    id integer generated by default as identity primary key,
    name  varchar(30) not null unique,
    author varchar(50) not null,
    year integer not null,
    person_id integer references person(id) on delete set null,
    capture_date timestamp
);