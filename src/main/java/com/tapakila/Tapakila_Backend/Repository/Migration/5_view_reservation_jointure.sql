CREATE OR REPLACE VIEW reservation_details AS
SELECT
    r.reservation_id,
    u.user_name,
    u.user_id,
    u.user_email,
    e.event_title,
    t.ticket_id,
    t.ticket_type,
    r.reservation_date
FROM
    reservations r
JOIN
    tickets t ON r.ticket_id = t.ticket_id
JOIN
    events e ON r.event_id = e.event_id
JOIN
    users u ON r.user_id = u.user_id;


SELECT reservation_id, user_id, ticket_id, reservation_date from reservation_details;