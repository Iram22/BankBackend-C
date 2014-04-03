DROP TABLE checking_accounts;
DROP TABLE transfers;
DROP TABLE accounts;
DROP TABLE persons; 
DROP TABLE postals;

DROP SEQUENCE transfer_sequence restrict;

--------------------------------------

CREATE SEQUENCE transfer_sequence as int start with 100 increment by 1;

CREATE TABLE postals(
    code VARCHAR(10) NOT NULL PRIMARY KEY, 
    district VARCHAR(25)
);

CREATE TABLE persons(
    cpr VARCHAR(15) NOT NULL PRIMARY KEY,
    title VARCHAR(5),
    firstName VARCHAR(20),
    lastName VARCHAR(20),
    street VARCHAR(25),
    phone VARCHAR(20),
    email VARCHAR(20),
    password VARCHAR(64),
    postalCode VARCHAR(10) REFERENCES postals(code)
);


CREATE TABLE accounts(
    number VARCHAR(25) NOT NULL PRIMARY KEY,
    balance FLOAT,
    interest FLOAT,
    dtype VARCHAR(30),
    owner VARCHAR(15) REFERENCES persons(cpr)
);

CREATE TABLE transfers(
    id INT PRIMARY KEY, 
    amount FLOAT,
    date DATE,
    source VARCHAR(25) REFERENCES accounts(number),
    target VARCHAR(25) REFERENCES accounts(number)
);

CREATE TABLE checking_accounts(
    number VARCHAR(25) REFERENCES accounts(number),
    PRIMARY KEY(number)
);