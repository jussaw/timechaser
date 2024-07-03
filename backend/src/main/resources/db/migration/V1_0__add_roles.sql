-- Insert roles into the Roles table with created_at and updated_at fields
INSERT INTO role (name, deleted, created_at, updated_at) VALUES ('Admin', false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO role (name, deleted, created_at, updated_at) VALUES ('Manager', false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());
INSERT INTO role (name, deleted, created_at, updated_at) VALUES ('Employee', false, CURRENT_TIMESTAMP(), CURRENT_TIMESTAMP());



