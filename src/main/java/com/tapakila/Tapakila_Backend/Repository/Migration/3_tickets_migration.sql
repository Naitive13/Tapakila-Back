
CREATE TABLE tickets(
    ticket_id UUID PRIMARY KEY ,
    event_id UUID references events(event_id) ON DELETE CASCADE,
    reservation_status reservation_status,
    ticket_type ticket_type
);
