INSERT INTO reservations (reservation_id, user_id, ticket_id, reservation_date) VALUES
(gen_random_uuid(), (SELECT user_id FROM users WHERE user_name = 'taratra'), (SELECT ticket_id FROM tickets WHERE ticket_type = 'gold'), '2023-10-10 10:00:00'),
(gen_random_uuid(), (SELECT user_id FROM users WHERE user_name = 'titan'), (SELECT ticket_id FROM tickets WHERE ticket_type = 'silver'), '2023-10-11 11:00:00'),
(gen_random_uuid(), (SELECT user_id FROM users WHERE user_name = 'neswilly'), (SELECT ticket_id FROM tickets WHERE ticket_type = 'bronze'), '2023-10-12 12:00:00'),
(gen_random_uuid(), (SELECT user_id FROM users WHERE user_name = 'randy'), (SELECT ticket_id FROM tickets WHERE ticket_type = 'regular'), '2023-10-13 13:00:00');
