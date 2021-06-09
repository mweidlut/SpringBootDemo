drop database if exists test;
create database test;

drop table if exists test.t_customer;
create table test.t_customer(
  id bigint not null auto_increment primary key,
  first_name varchar(255) not null,
  age varchar(5)
);