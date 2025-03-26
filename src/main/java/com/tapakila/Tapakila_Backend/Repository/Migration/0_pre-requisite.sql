CREATE EXTENSION IF NOT EXISTS pgcrypto;
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

