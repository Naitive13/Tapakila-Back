DROP database  tapakila_db;
create DATABASE tapakila_db;
CREATE EXTENSION IF NOT EXISTS pgcrypto;

\c tapakila_db;

create type reservation_status as ENUM('reserved', 'available');
create type user_type as ENUM('standard', 'admin');
create type ticket_type as ENUM('vip', 'gold', 'silver', 'bronze', 'regular');
create type event_type as ENUM (
    'CONCERT',
    'SPORT',
    'THEATER',
    'CONFERENCE',
    'FESTIVAL',
    'WORKSHOP',
    'EXHIBITION',
    'COMEDY',
    'MOVIE',
    'OTHER'
);

CREATE TABLE users(
    user_id UUID PRIMARY KEY ,
    user_name varchar(50) UNIQUE NOT NULL,
    user_email varchar(50) UNIQUE NOT NULL,
    user_password varchar(100) NOT NULL,
    user_creation_date TIMESTAMP without TIME ZONE,
    user_type user_type
);

CREATE TABLE events(
    event_id UUID PRIMARY KEY ,
    event_title varchar(50) NOT NULL,
    event_type event_type,
    event_date TIMESTAMP without TIME ZONE
);

CREATE TABLE tickets(
    ticket_id UUID PRIMARY KEY ,
    event_id UUID references events(event_id) ON DELETE CASCADE,
    reservation_status reservation_status,
    ticket_type ticket_type
);

CREATE TABLE reservations(
    reservation_id UUID PRIMARY KEY ,
    event_id UUID references events(event_id) ON DELETE CASCADE,
    user_id UUID references users(user_id) ON DELETE CASCADE,
    ticket_id UUID references tickets(ticket_id) ON DELETE CASCADE,
    reservation_date TIMESTAMP without TIME ZONE
);

