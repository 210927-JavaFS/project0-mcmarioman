DROP TABLE user_roles;
CREATE TABLE user_roles(
	User_Role_Id integer PRIMARY key,
	User_Role_Name varchar(15) NOT null
)

INSERT INTO user_roles VALUES (1, 'Customer');
INSERT INTO user_roles VALUES (2, 'Employee');
INSERT INTO user_roles VALUES (3, 'Admin');

DROP TABLE users;
CREATE TABLE users (
	User_Id serial PRIMARY KEY,
	User_Role_Id integer NOT NULL REFERENCES user_roles(User_Role_Id),
	User_Name varchar(25) NOT NULL UNIQUE,
	User_Password varchar(64) NOT NULL,
	first_name varchar(25) NOT NULL,
	last_name varchar(25) NOT NULL,
	Date_Created date NOT null
);


CREATE TABLE customer (
	user_id integer PRIMARY KEY REFERENCES users (user_id),
	email varchar(35) NOT NULL,
	phone_number varchar(12) NOT NULL
);


CREATE TABLE account_type(
	Account_Type_Id integer PRIMARY KEY,
	Account_Type_Name varchar(15) NOT null
);

INSERT INTO account_type (Account_Type_Id, Account_Type_Name) VALUES (1, 'Checking');
INSERT INTO account_type (Account_Type_Id, Account_Type_Name) VALUES (2, 'Saving');

CREATE TABLE account_status(
	account_status_id integer PRIMARY KEY,
	account_status_name varchar(20) NOT null
);

INSERT INTO account_status (account_status_id, account_status_name) VALUES (1, 'Pending Approval');
INSERT INTO account_status (account_status_id, account_status_name) VALUES (2, 'Active');
INSERT INTO account_status (account_status_id, account_status_name) VALUES (3, 'Canceled');
INSERT INTO account_status (account_status_id, account_status_name) VALUES (4, 'Denied');


CREATE TABLE account (
	account_number serial PRIMARY KEY,
	account_type_id integer REFERENCES account_type(Account_Type_Id),
	User_Id integer REFERENCES users (User_Id),
	balance numeric(11,2) NOT NULL,
	account_status_id integer REFERENCES account_status(account_status_id),
	date_opened date NOT null,
	date_approved date
)


create or replace procedure transfer_funds(
   account_from int,
   account_to int, 
   amount NUMERIC(11, 2)
)
language plpgsql    
as $$
	begin
    -- subtracting the amount from the sender's account 
    update account set balance = balance - amount where account_number = account_from;

    -- adding the amount to the receiver's account
    update account set balance = balance + amount where account_number = account_to;

    commit;
end;$$

create or replace procedure insert_customer(
   _user_name varchar(25),
   _user_password varchar(64),
   _first_name varchar(25),
   _last_name varchar(25),
   _email varchar(35),
   _phone_number varchar(12)
)
language plpgsql    
as $$
DECLARE
   _u_id integer;
	begin
    -- subtracting the amount from the sender's account 
    INSERT INTO users (user_role_id, user_name, user_password, date_created, first_name, last_name)
    VALUES (1, _user_name, _user_password, now(), _first_name, _last_name ) RETURNING user_id INTO _u_id;

    -- adding the amount to the receiver's account
    INSERT INTO customer (user_id, email, phone_number)
    VALUES (_u_id ,_email, _phone_number);

    commit;
end;$$

