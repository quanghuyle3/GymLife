USE gym_life;

-- Insert initial roles
INSERT INTO role (name) VALUES ('ROLE_OWNER'), ('ROLE_MANAGER'), ('ROLE_EMPLOYEE'), ('ROLE_TRAINER'), ('ROLE_GYMMER');

-- Insert an initial bank account
INSERT INTO bank_account (account_number, bank_name, routine_number) VALUES ('12345678911234567', 'Wells Fargo', 121042882);

-- Insert the owner to member table
-- default password: test123
INSERT INTO member (email, username, password, first_name, last_name, address, phone_number, date_of_birth, gender, date_join, bank_account_number)
 		VALUES ('owneraccount@gmail.com', 'owneraccount', '{bcrypt}$2a$10$24czbWIgNO9fpEm9ndG0feCPJ4yFNpb3X9P2xeTp.UNMgmh4rWmhy', 'Owner', 'Account', '4th Street, Stockton, CA 95210', '2097689876', '1990-10-20', 'Male', '2023-05-22', '12345678911234567');
        
-- Assign role OWNER to owner
INSERT INTO role_member (role_id, member_id) VALUES (1, 1000000);