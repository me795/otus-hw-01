-- Для @GeneratedValue(strategy = GenerationType.IDENTITY)

create table clients
(
    id   bigserial not null primary key,
    name varchar(50),
    address_id bigint
);

create table addresses
(
    id   bigserial not null primary key,
    street varchar(50)
);


create table phones
(
    id   bigserial not null primary key,
    number varchar(11),
    client_id bigint
);
