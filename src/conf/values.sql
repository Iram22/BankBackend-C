delete from Checking_Accounts;
delete from Transfers;
delete from Accounts;
delete from Persons;
delete from Postals;

----- Postals -----
insert into Postals(code, district)
values 
('3000', 'Helsingør'),
('3070', 'Helsingør'),
('2980', 'Kokkedal');


----- Persons -----
insert into Persons(cpr, title, firstName, lastName, street, phone, email, password, postalCode)
values
('121256-0555','Hr.','Kurt','Hansen','Solvej 8', '60624190','kurt@mail.dk','test','3000'),
('010256-0777','Hr.','James','Bond','Frejasvej 20', '34241900','james@mail.dk','test','2980'),
('111190-0444','Fr.','Sonja','Jensen','Månegade 9', '30524190','sonja@mail.dk','test','3070');


----- Accounts -----
insert into Accounts(number, balance, interest, dtype, owner)
values
('4711-004', 1040, 0.5, 'CheckingAccount', '010256-0777'),
('4711-005', 1500, 0.5, 'CheckingAccount', '121256-0555'),
('4711-006', 2000, 0.5, 'CheckingAccount',  '121256-0555'),
('4711-007',  900, 0.5, 'CheckingAccount',  '111190-0444');

----- Transfers -----
insert into Transfers(id, amount, date, source, target)
values
(02, 200, '2013-11-20', '4711-005', '4711-004'),
(03, 200, '2013-03-13', '4711-007', '4711-005'),
(04, 200, '2013-12-04', '4711-004', '4711-006');

----- Accounts -----
insert into Checking_Accounts(number)
values
('4711-004'),
('4711-005'),
('4711-006'),
('4711-007');

