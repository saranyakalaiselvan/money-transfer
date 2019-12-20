CREATE TABLE account (
account_number VARCHAR(36) PRIMARY KEY NOT NULL,
username VARCHAR(50) NOT NULL,
balance DECIMAL(13, 4) NOT NULL,
created_at TIMESTAMP NOT NULL,
updated_at TIMESTAMP,
constraint fk_user_information foreign key(username) references user_profiles(username));
