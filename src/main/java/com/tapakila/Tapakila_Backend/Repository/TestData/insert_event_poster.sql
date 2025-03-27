-- Update existing events with event_poster
UPDATE events
SET event_poster = 'https://example.com/rock_the_city.jpg'
WHERE event_title = 'Rock the City Concert';

UPDATE events
SET event_poster = 'https://example.com/summer_festival.jpg'
WHERE event_title = 'Summer Music Festival';

UPDATE events
SET event_poster = 'https://example.com/hamlet.jpg'
WHERE event_title = 'Hamlet Theatre Performance';

UPDATE events
SET event_poster = 'https://example.com/jazz_night.jpg'
WHERE event_title = 'Jazz Night Live';

UPDATE events
SET event_poster = 'https://example.com/winter_lights.jpg'
WHERE event_title = 'Winter Lights Festival';