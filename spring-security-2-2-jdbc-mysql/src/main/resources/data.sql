insert into users (username, password, enabled) values ('user', '123456', true);
insert into users (username, password, enabled) values ('admin', '123456', true);

insert into authorities (username, authority) values ('user', 'ROLE_USER');
insert into authorities (username, authority) values ('admin', 'ROLE_USER');
insert into authorities (username, authority) values ('admin', er'ROLE_ADMIN');