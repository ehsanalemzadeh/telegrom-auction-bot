create table offers
(
    id          bigint auto_increment primary key,
    username    varchar not null,
    name        varchar,
    price       bigint,
    description varchar,
    type        varchar,
    create_date timestamp,
    update_date timestamp
);