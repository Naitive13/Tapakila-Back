-- Add ENUM type for event_status
CREATE TYPE event_status
    AS ENUM ('To come', 'In progress', 'Past', 'Cancelled');

-- Add event_status column to events table
ALTER TABLE events
ADD COLUMN event_status event_status DEFAULT 'To come';
