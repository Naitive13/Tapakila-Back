-- Add ENUM type for event_status
CREATE TYPE event_status
    AS ENUM ('UPCOMING', 'ONGOING', 'PAST', 'CANCELLED');

-- Add event_status column to events table
ALTER TABLE events
ADD COLUMN event_status event_status DEFAULT 'UPCOMING';
