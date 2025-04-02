CREATE TABLE reservations(
    reservation_id UUID PRIMARY KEY ,
    user_id UUID references users(user_id) ON DELETE CASCADE,
    ticket_id UUID references tickets(ticket_id) ON DELETE CASCADE,
    reservation_date TIMESTAMP without TIME ZONE
);

