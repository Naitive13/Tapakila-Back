CREATE TABLE reservations(
    reservation_id UUID PRIMARY KEY ,
    event_id UUID references events(event_id) ON DELETE CASCADE,
    user_id UUID references users(user_id) ON DELETE CASCADE,
    ticket_id UUID references tickets(ticket_id) ON DELETE CASCADE,
    reservation_date TIMESTAMP without TIME ZONE
);

