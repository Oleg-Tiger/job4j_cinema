CREATE TABLE if not exists sessions (
  id SERIAL PRIMARY KEY,
  name text,
  photo bytea
);

comment on table sessions is 'Доступные сеансы(фильмы) кинотеатра';
comment on column sessions.id is 'Идентификатор сеанса';
comment on column sessions.name is 'Название фильма';
comment on column sessions.photo is 'Постер фильма, отображаемый на сайте';