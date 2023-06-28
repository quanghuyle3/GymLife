DROP TABLE IF EXISTS bank_account;
CREATE TABLE bank_account (
		account_number VARCHAR(50) NOT NULL PRIMARY KEY,
        bank_name VARCHAR(100) NOT NULL,
        routine_number INT NOT NULL,
        remaining_balance_owed DOUBLE DEFAULT 0.0);
        
DROP TABLE IF EXISTS manufacture ;
CREATE TABLE manufacture (
		id INT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(100) NOT NULL,
        address VARCHAR(150) NOT NULL,
        phone_number VARCHAR(15),
        email VARCHAR(100),
        bank_account_number VARCHAR(50),
        CONSTRAINT bank_account_fk FOREIGN KEY (bank_account_number) REFERENCES bank_account(account_number));
INSERT INTO manufacture (name, address, email, bank_account_number) VALUES ("One Member", '9443 Ravenna Ln, Stockton, CA 95212', 'yes@gmail.com', '12345678911234568');

DROP TABLE IF EXISTS transaction;
CREATE TABLE transaction (
		id INT AUTO_INCREMENT PRIMARY KEY,
        account_send VARCHAR(50) NOT NULL,
        account_receive VARCHAR(50) NOT NULL,
        amount DOUBLE NOT NULL,
        date DATE NOT NULL,
        CONSTRAINT bank_account_fk_1 FOREIGN KEY (account_send) REFERENCES bank_account(account_number),
        CONSTRAINT bank_account_fk_2 FOREIGN KEY (account_receive) REFERENCES bank_account(account_number));
INSERT INTO transaction (account_send, account_receive, amount, date) VALUES ('12345678911234567', '12345678911234568', 150.50, '2023-12-30');
        
DROP TABLE IF EXISTS equipment;
CREATE TABLE equipment (
		serials VARCHAR(30) PRIMARY KEY,
        name VARCHAR(100) NOT NULL,
        target VARCHAR(50),
        manufacture_id INT,
        transaction_id INT, 
        date_imported DATE,
        CONSTRAINT manufacture_fk FOREIGN KEY (manufacture_id) REFERENCES manufacture(id),
        CONSTRAINT transaction_fk FOREIGN KEY (transaction_id) REFERENCES transaction(id));
INSERT INTO equipment (serials, name, target, transaction_id, date_imported) VALUES ('3372098749', 'Bench Press Machine', 'chess', 1, '2023-12-19');
        
DROP TABLE IF EXISTS membership;
CREATE TABLE membership (
		id INT AUTO_INCREMENT PRIMARY KEY,
        type_name VARCHAR(30) NOT NULL,
        price DOUBLE NOT NULL);
INSERT INTO membership (type_name, price) VALUES ('bronze', 20), ('platinium', 50);

DROP TABLE IF EXISTS member;
CREATE TABLE member (
		id INT AUTO_INCREMENT PRIMARY KEY,
        email VARCHAR(100) NOT NULL,
        username VARCHAR(50) NOT NULL UNIQUE,
        password VARCHAR(72) NOT NULL,
        first_name VARCHAR(25) NOT NULL,
        last_name VARCHAR(25) NOT NULL,
        address VARCHAR(150) NOT NULL,
        phone_number VARCHAR(15) NOT NULL,
        date_of_birth DATE NOT NULL,
        gender VARCHAR(11) NOT NULL,
        membership_type INT,
        date_join DATE NOT NULL, 
        date_expiration DATE,
        active BOOLEAN DEFAULT TRUE,
        bank_account_number VARCHAR(50) NOT NULL,
        CONSTRAINT membership_fk FOREIGN KEY (membership_type) REFERENCES membership(id),
        CONSTRAINT bank_account FOREIGN KEY (bank_account_number) REFERENCES bank_account(account_number)
        ) AUTO_INCREMENT = 1000000;
INSERT INTO member (email, username, password, first_name, last_name, address, phone_number, date_of_birth, gender, membership_type, date_join, bank_account_number)
		VALUES ('johnphan@gmail.com', 'johnphan', '{noop}test123', 'john', 'phan', '9443 Ravennaln', '2097689876-1234', '2000-12-12', 'male', 2, '2023-12-30', '12345678911234568');
INSERT INTO member (email, username, password, first_name, last_name, address, phone_number, date_of_birth, gender, membership_type, date_join, bank_account_number)
		VALUES ('kariktran@gmail.com', 'kariktran', '{noop}test123', 'karik', 'tran', '9443 Ravennaln', '2097689876-1233', '2000-12-13', 'female', 1, '2023-12-31', '12345678911234567');    
        
DROP TABLE IF EXISTS role;
CREATE TABLE role (
		id INT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(25) NOT NULL,
        wage DOUBLE );
INSERT INTO role (name, wage) VALUES ('employee', 18), ('owner', 45);
        

        
        