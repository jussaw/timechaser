-- Insert admin user into the User table
INSERT INTO user (username, password, first_name, last_name, deleted, created_at, updated_at)
VALUES ('admin', '$2a$10$DO5XRGvq4VFPExduDTCemO4O4Vj5thXhli5h318FKa2MpYheeyFdi', 'Admin', 'User', false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());

-- Assign the Admin role to the admin user
INSERT INTO user_role (user_id, role_id)
SELECT u.id, r.id
FROM user u, role r
WHERE u.username = 'admin' AND r.name = 'Admin';
