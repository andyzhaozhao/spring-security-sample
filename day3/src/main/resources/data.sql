insert into users( username , password ,enabled ,phone,email) values('1','1',true,'111','111');
insert into users( username , password ,enabled ,phone,email) values('2','2',true,'222','222');
insert into users( username , password ,enabled ,phone,email) values('3','3',true,'333','333');
insert into users( username , password ,enabled ,phone,email) values('4','4',true,'444','444');


insert into authorities(username, authority) values('1','ROLE_USER');
insert into authorities(username, authority) values('2','ROLE_ADMIN');
insert into authorities(username, authority) values('3','READ');
insert into authorities(username, authority) values('4','READ');

insert into permissions(authority, url) values('ROLE_USER','/');
insert into permissions(authority, url) values('ROLE_ADMIN','/user');
insert into permissions(authority, url) values('ROLE_ADMIN','/admin');



