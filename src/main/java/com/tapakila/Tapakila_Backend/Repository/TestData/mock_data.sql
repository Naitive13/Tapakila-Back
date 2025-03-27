-- Enable pgcrypto extension (if not already enabled)
CREATE EXTENSION IF NOT EXISTS pgcrypto;

-- Insert into users table with gen_random_uuid()
INSERT INTO users (user_id, user_name, user_email, user_password, user_creation_date, user_type) VALUES
(gen_random_uuid(), 'taratra', 'taratra.fanoitra@example.com', 'hashed_password_1', '2023-10-01 12:00:00', 'standard'),
(gen_random_uuid(), 'jane_smith', 'jane.smith@example.com', 'hashed_password_2', '2023-10-02 13:00:00', 'standard'),
(gen_random_uuid(), 'alice_wang', 'alice.wang@example.com', 'hashed_password_3', '2023-10-03 14:00:00', 'admin'),
(gen_random_uuid(), 'bob_jones', 'bob.jones@example.com', 'hashed_password_4', '2023-10-04 15:00:00', 'standard'),
(gen_random_uuid(), 'emily_rose', 'emily.rose@example.com', 'hashed_password_5', '2023-10-05 16:00:00', 'standard');

-- Insert into events table with gen_random_uuid()
INSERT INTO events (event_id, event_title, event_type, event_date) VALUES
(gen_random_uuid(), 'Rock the City Concert', 'CONCERT', '2023-11-15 20:00:00'),
(gen_random_uuid(), 'Summer Music Festival', 'FESTIVAL', '2023-12-01 12:00:00'),
(gen_random_uuid(), 'Hamlet Theatre Performance', 'THEATER', '2023-11-20 19:30:00'),
(gen_random_uuid(), 'Jazz Night Live', 'CONCERT', '2023-11-25 21:00:00'),
(gen_random_uuid(), 'Winter Lights Festival', 'FESTIVAL', '2023-12-10 18:00:00');

-- Insert into tickets table with gen_random_uuid()
INSERT INTO tickets (ticket_id, event_id, reservation_status, ticket_type) VALUES
(gen_random_uuid(), (SELECT event_id FROM events WHERE event_title = 'Rock the City Concert'), 'available', 'vip'),
(gen_random_uuid(), (SELECT event_id FROM events WHERE event_title = 'Rock the City Concert'), 'reserved', 'gold'),
(gen_random_uuid(), (SELECT event_id FROM events WHERE event_title = 'Summer Music Festival'), 'available', 'silver'),
(gen_random_uuid(), (SELECT event_id FROM events WHERE event_title = 'Hamlet Theatre Performance'), 'reserved', 'bronze'),
(gen_random_uuid(), (SELECT event_id FROM events WHERE event_title = 'Jazz Night Live'), 'available', 'regular');

-- Insert into reservations table with gen_random_uuid()
INSERT INTO reservations (reservation_id, event_id, user_id, ticket_id, reservation_date) VALUES
(gen_random_uuid(), (SELECT event_id FROM events WHERE event_title = 'Rock the City Concert'), (SELECT user_id FROM users WHERE user_name = 'john_doe'), (SELECT ticket_id FROM tickets WHERE ticket_type = 'gold'), '2023-10-10 10:00:00'),
(gen_random_uuid(), (SELECT event_id FROM events WHERE event_title = 'Summer Music Festival'), (SELECT user_id FROM users WHERE user_name = 'jane_smith'), (SELECT ticket_id FROM tickets WHERE ticket_type = 'silver'), '2023-10-11 11:00:00'),
(gen_random_uuid(), (SELECT event_id FROM events WHERE event_title = 'Hamlet Theatre Performance'), (SELECT user_id FROM users WHERE user_name = 'alice_wang'), (SELECT ticket_id FROM tickets WHERE ticket_type = 'bronze'), '2023-10-12 12:00:00'),
(gen_random_uuid(), (SELECT event_id FROM events WHERE event_title = 'Jazz Night Live'), (SELECT user_id FROM users WHERE user_name = 'bob_jones'), (SELECT ticket_id FROM tickets WHERE ticket_type = 'regular'), '2023-10-13 13:00:00'),
(gen_random_uuid(), (SELECT event_id FROM events WHERE event_title = 'Winter Lights Festival'), (SELECT user_id FROM users WHERE user_name = 'emily_rose'), (SELECT ticket_id FROM tickets WHERE ticket_type = 'vip'), '2023-10-14 14:00:00');