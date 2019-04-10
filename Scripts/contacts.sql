-- Drop table

DROP TABLE IF exists public.contacts;

-- Create table

CREATE TABLE public.contacts (
    id int not null IDENTITY,
    first_name varchar(100),
    last_name varchar(100),
    email varchar(100),
    street varchar(100),
    city varchar(100),
    state varchar(100),
    postal varchar(10),
    country varchar(100),
    primary key (id)
);