-- Для @GeneratedValue(strategy = GenerationType.IDENTITY)

create table clients
(
    id   bigserial not null primary key,
    name varchar(50),
    deleted_at timestamp without time zone
);

create table addresses
(
    id   bigserial not null primary key,
    street varchar(50),
    client_id bigint,
    deleted_at timestamp without time zone
);


create table phones
(
    id   bigserial not null primary key,
    number varchar(11),
    client_id bigint,
    deleted_at timestamp without time zone
);
