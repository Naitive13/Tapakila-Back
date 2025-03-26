CREATE TABLE events(
    event_id UUID PRIMARY KEY ,
    event_title varchar(50) NOT NULL,
    event_type event_type,
    event_date TIMESTAMP without TIME ZONE
);
