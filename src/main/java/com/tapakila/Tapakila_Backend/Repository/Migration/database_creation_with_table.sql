--\i 'D:/HEI/WEB3/Tapakila/Tapakila-Back/src/main/java/com/tapakila/Tapakila_Backend/Repository/Migration/database_creation_with_table.sql'
DROP database  tapakila_db;
create DATABASE tapakila_db;

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
    user_id BIGSERIAL primary key,
    user_name varchar(50) UNIQUE NOT NULL,
    user_email varchar(50) UNIQUE NOT NULL,
    user_password varchar(100) NOT NULL,
    user_type user_type
);

CREATE TABLE events(
    event_id BIGSERIAL PRIMARY KEY,
    event_title varchar(50) NOT NULL,
    event_type event_type,
    event_date TIMESTAMP without TIME ZONE
);

CREATE TABLE tickets(
    ticket_id BIGSERIAL PRIMARY KEY,
    event_id BIGINT references events(event_id),
    reservation_status reservation_status
);

CREATE TABLE reservations(
    reservation_id BIGSERIAL PRIMARY KEY,
    event_id BIGINT references events(event_id),
    ticket_id BIGINT references tickets(ticket_id),
    reservation_date TIMESTAMP without TIME ZONE
);

