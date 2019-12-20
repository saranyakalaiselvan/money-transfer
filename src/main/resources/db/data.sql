create database bank_info;
create user 'saran' identified by 'saran';
grant all on bank_info.* to 'saran';

# admin user (password: admin)
insert into users (username, password, enabled, created_at) values ('saran', 'saran', 1, current_timestamp());

# admin user authorities
insert into authorities (username, authority, created_at) values ('saran', 'ADMINISTRATOR', current_timestamp());
insert into authorities (username, authority, created_at) values ('saran', 'CUSTOMER', current_timestamp());

# admin user profile
insert into user_profiles (username, email, phone_number, created_at) values ('saran', 'admin@bankinfo.com', '0987654321', current_timestamp());