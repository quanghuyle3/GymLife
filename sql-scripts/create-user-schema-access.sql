-- Drop exist user
DROP USER IF EXISTS 'gymlife'@'localhost';

-- Create new user
CREATE USER 'gymlife'@'localhost' IDENTIFIED BY 'gymlife';

-- Create a new database schema
CREATE SCHEMA IF NOT EXISTS gym_life;

-- Grant privileges for this user to new schema
GRANT ALL PRIVILEGES ON gym_life.* TO 'gymlife'@'localhost';