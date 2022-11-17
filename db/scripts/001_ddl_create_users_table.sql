CREATE TABLE if not exists users (
  id SERIAL PRIMARY KEY,
  username VARCHAR NOT NULL,
  email VARCHAR NOT NULL UNIQUE,
  phone VARCHAR NOT NULL UNIQUE
);

comment on table users is 'Пользователи сайта кинотеатра';
comment on column users.id is 'Идентификатор пользователя';
comment on column users.email is 'Электронная почта пользователя';
comment on column users.phone is 'Номер телефона пользователя';