CREATE TABLE users(
	username varchar_ignorecase(50) NOT NULL PRIMARY KEY ,
	PASSWORD varchar_ignorecase(500) NOT NULL ,
	enabled boolean NOT NULL,
  phone varchar_ignorecase(500) NOT NULL ,
  email varchar_ignorecase(500) NOT NULL
);

CREATE TABLE authorities(
	username varchar_ignorecase(50) NOT NULL ,
	authority varchar_ignorecase(50) NOT NULL ,
	CONSTRAINT fk_authorities_users FOREIGN KEY(username) REFERENCES users(username)
);

-- CREATE UNIQUE INDEX ix_auth_username ON authorities(username , authority);

CREATE TABLE permissions(
	authority varchar_ignorecase(50) NOT NULL ,
	url varchar_ignorecase(500) NOT NULL
-- 	CONSTRAINT fk_permissions_authorities FOREIGN KEY(authority) REFERENCES authorities(authority)
);

-- CREATE UNIQUE INDEX ix_auth_url ON permissions(authority , url);