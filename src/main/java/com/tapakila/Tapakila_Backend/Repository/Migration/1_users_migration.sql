CREATE TABLE users(
    user_id UUID PRIMARY KEY ,
    user_name varchar(50) UNIQUE NOT NULL,
    user_email varchar(50) UNIQUE NOT NULL,
    user_password varchar(100) NOT NULL,
    user_creation_date TIMESTAMP without TIME ZONE,
    user_type user_type
);
