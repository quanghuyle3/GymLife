USE gym_life;

-- Insert initial roles
INSERT INTO role (name, wage) VALUES ('ROLE_OWNER', 0), ('ROLE_MANAGER', 32), ('ROLE_EMPLOYEE', 22), ('ROLE_TRAINER', 26), ('ROLE_GYMMER', 0);

-- Insert initial membership
INSERT INTO membership (type_name, price) VALUES ('BASIC', 20), ('PREMIUM', 30), ('ALL ACCESS', 36);

-- Insert an initial bank account
INSERT INTO bank_account (account_number, bank_name, routine_number) VALUES ('9845278645', 'Wells Fargo', 121042882);

-- Insert the owner to member table
-- default password: test123
INSERT INTO member (email, username, password, first_name, last_name, address, phone_number, date_of_birth, gender, date_join, bank_account_number)
 		VALUES ('owneraccount.gymlife@gmail.com', 'owneraccount', '$2a$10$24czbWIgNO9fpEm9ndG0feCPJ4yFNpb3X9P2xeTp.UNMgmh4rWmhy', 'Quang', 'Le', '2828 4th Street, San Jose, CA 95121', '2096887776', '1990-10-20', 'Male', '2023-06-01', '9845278645');
        
-- Assign role OWNER to owner
INSERT INTO role_member (role_id, member_id) VALUES (1, 1000000);