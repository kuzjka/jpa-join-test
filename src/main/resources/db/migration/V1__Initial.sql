create sequence default_sequence increment by 50;

create table types (id int primary key, name varchar);
create table brands (id int primary key, name varchar);
create table products (id int primary key, name varchar, type_id int, brand_id int, price int);
