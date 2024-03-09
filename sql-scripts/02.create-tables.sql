USE gym_life;

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

DROP TABLE IF EXISTS transaction;
CREATE TABLE transaction (
		id INT AUTO_INCREMENT PRIMARY KEY,
        account_send VARCHAR(50) NOT NULL,
        account_receive VARCHAR(50) NOT NULL,
        amount DOUBLE NOT NULL,
        date DATE NOT NULL,
        CONSTRAINT bank_account_fk_1 FOREIGN KEY (account_send) REFERENCES bank_account(account_number),
        CONSTRAINT bank_account_fk_2 FOREIGN KEY (account_receive) REFERENCES bank_account(account_number));
        
DROP TABLE IF EXISTS equipment;
CREATE TABLE equipment (
		serials VARCHAR(30) PRIMARY KEY,
        name VARCHAR(100) NOT NULL,
        target VARCHAR(50),
        transaction_id INT NOT NULL, 
        date_imported DATE,
        CONSTRAINT transaction_fk FOREIGN KEY (transaction_id) REFERENCES transaction(id));
        
DROP TABLE IF EXISTS membership;
CREATE TABLE membership (
		id INT AUTO_INCREMENT PRIMARY KEY,
        type_name VARCHAR(30) NOT NULL UNIQUE,
        price DOUBLE NOT NULL);


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
        bank_account_number VARCHAR(50),
        CONSTRAINT membership_fk FOREIGN KEY (membership_type) REFERENCES membership(id),
        CONSTRAINT bank_account FOREIGN KEY (bank_account_number) REFERENCES bank_account(account_number)
        ) AUTO_INCREMENT = 1000000;  
        
DROP TABLE IF EXISTS role;
CREATE TABLE role (
		id INT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(25) NOT NULL UNIQUE,
        wage DOUBLE DEFAULT 0);
        
DROP TABLE IF EXISTS role_member;
CREATE TABLE role_member (
		role_id INT,
        member_id INT,
        PRIMARY KEY (role_id, member_id),
        CONSTRAINT role_fk FOREIGN KEY (role_id) REFERENCES role(id),
        CONSTRAINT member_fk FOREIGN KEY (member_id) REFERENCES member(id));

DROP TABLE IF EXISTS training;
CREATE TABLE training (
		id INT AUTO_INCREMENT PRIMARY KEY,
        trainer_id INT NOT NULL,
        student_id INT NOT NULL,
        date_start DATE NOT NULL,
        date_end DATE,
        CONSTRAINT member_fk_1 FOREIGN KEY (trainer_id) REFERENCES member(id),
        CONSTRAINT member_fk_2 FOREIGN KEY (student_id) REFERENCES member(id),
        CONSTRAINT check_trainer_student_different CHECK (trainer_id <> student_id),
        CONSTRAINT check_date_order CHECK (date_start <= date_end OR date_end IS NULL));
        
        
DROP TABLE IF EXISTS work_schedule;
CREATE TABLE work_schedule (
		id INT AUTO_INCREMENT PRIMARY KEY,
        work_date DATE NOT NULL,
        staff_id INT NOT NULL,
        time_start TIME NOT NULL,
        time_end TIME NOT NULL,
        CONSTRAINT staff_fk FOREIGN KEY (staff_id) REFERENCES member(id),
        CONSTRAINT check_time_valid CHECK (time_end > time_start));

DROP TABLE IF EXISTS access_log;
CREATE TABLE access_log (
		id INT AUTO_INCREMENT PRIMARY KEY,
        date DATE NOT NULL,
        member_id INT NOT NULL,
        time_access_in TIME NOT NULL,
        time_access_out TIME,
        CONSTRAINT member_fk_constraint FOREIGN KEY (member_id) REFERENCES member(id),
        CONSTRAINT check_time_valid_constraint CHECK (time_access_out > time_access_in));
        
        