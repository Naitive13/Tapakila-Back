INSERT INTO users (user_id, user_name, user_email, user_password, user_creation_date, user_type) VALUES
(gen_random_uuid(), 'taratra', 'taratra.fanoitra@example.com', '$2a$10$AfoBm0t2MdvU6dDTrG620.EJYUimJkiPfXlxKObp3vbQcXe440/lO', '2023-10-01 12:00:00', 'standard'), -- (pswd : 'taratra123')
(gen_random_uuid(), 'titan', 'titan@example.com', '$2a$10$RD9TfE2.SHdBXSq.OCvdq.xv7GqGG.omh846/hqSIa5VdZHcZHSZS', '2023-10-02 13:00:00', 'standard'), -- (pswd : 'titan123')
(gen_random_uuid(), 'neswilly', 'neswilly@example.com', '$2a$10$WeS0cWkG3Lr7AG7ODdlQROcCE2xAW4D2c9Xj9rTl7f/tFmEqLW7Be', '2023-10-03 14:00:00', 'admin'), -- (pswd : 'neswilly123')
(gen_random_uuid(), 'randy', 'randys@example.com', '$2a$10$d/lniBGApIpXUh.x0uAmp.lu1CvgVouLMVwudGhgBmdWdTYUtw4eG', '2023-10-04 15:00:00', 'standard');-- (pswd : 'randy123');
