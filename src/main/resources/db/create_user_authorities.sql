CREATE TABLE user (
username VARCHAR(50) PRIMARY KEY NOT NULL,
password VARCHAR(100) NOT NULL,
enabled TINYINT(1) NOT NULL,
created_at TIMESTAMP NOT NULL,
updated_at TIMESTAMP);

CREATE TABLE authorities (
id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
username VARCHAR(50) NOT NULL,
authority VARCHAR(50) NOT NULL,
created_at TIMESTAMP NOT NULL,
updated_at TIMESTAMP,
constraint fk_authorities_user foreign key(username) references user(username));

create unique index ix_authorities_username_authority on authorities (username, authority);