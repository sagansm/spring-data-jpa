--liquibase formatted sql
--changeset ssagan:2

  create table if not exists users(
      id bigserial PRIMARY KEY,
      username varchar not null,
      password varchar not null);

  create table if not exists roles(
      id bigserial PRIMARY KEY,
      name varchar not null);

  create table if not exists user_role (
      user_id bigint not null,
      role_id bigint not null,
      constraint fk_user_role_users foreign key(user_id) references users(id),
      constraint fk_user_role_role foreign key(role_id) references roles(id)
      );

insert into users(username, password) values
('ssagan', 'password'),
('admin', 'password');

insert into roles(name) values
('ROLE_USER'),
('ROLE_ADMIN');

insert into user_role(user_id, role_id) values
(1,1),
(2,1),
(2,2);