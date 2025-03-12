-- Users
insert into users (user_name, user_email, user_password, user_type)
values
    ('john_doe', 'john.doe@example.com', 'password123', 'standard'),
    ('jane_smith', 'jane.smith@example.com', 'password456', 'admin');

-- Events
insert into events (event_title, event_type, event_date)
values
    ('Rock Concert 2023', 'CONCERT', '2023-12-15 20:00:00'),
    ('Hamlet Play', 'THEATER', '2023-11-20 19:30:00'),
    ('Summer Music Festival', 'FESTIVAL', '2023-07-25 12:00:00');

-- tickets
INSERT INTO tickets (event_id, reservation_status, ticket_type)
VALUES
    (1, 'available', 'vip'), -- Rock Concert 2023
    (1, 'available', 'gold'),
    (2, 'available', 'silver'), -- Hamlet Play
    (2, 'reserved', 'regular'),
    (3, 'available', 'bronze'), -- Summer Music Festival
    (3, 'available', 'regular');

-- reservation
insert into reservations (user_id, event_id, ticket_id, reservation_date)
values
    (1, 2, 4, '2023-11-01 10:00:00'); -- Reserved ticket for Hamlet Play
