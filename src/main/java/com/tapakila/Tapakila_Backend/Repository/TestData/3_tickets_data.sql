INSERT INTO tickets (ticket_id, event_id, reservation_status, ticket_type) VALUES
(gen_random_uuid(), (SELECT event_id FROM events WHERE event_title = 'Rock the City Concert'), 'available', 'vip'),
(gen_random_uuid(), (SELECT event_id FROM events WHERE event_title = 'Rock the City Concert'), 'reserved', 'gold'),
(gen_random_uuid(), (SELECT event_id FROM events WHERE event_title = 'Summer Music Festival'), 'available', 'silver'),
(gen_random_uuid(), (SELECT event_id FROM events WHERE event_title = 'Hamlet Theatre Performance'), 'reserved', 'bronze'),
(gen_random_uuid(), (SELECT event_id FROM events WHERE event_title = 'Jazz Night Live'), 'available', 'regular');
