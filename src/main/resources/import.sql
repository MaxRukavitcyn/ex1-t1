insert into client (city, gender, name, id) values ('Kiev', 0, 'Adam Budzinski', 1)
insert into account (balance, overdraft, dtype, id) values (-500, 1500, 'CheckingAccount', 1)
insert into client_accounts (client_id, accounts_id) values (1, 1)
update client set active_account_id = 1 where id = 1;

insert into client (city, gender, name, id) values ('New York', 1, 'Anna Smith', 2)
insert into account (balance, dtype, id) values (1000, 'SavingAccount', 2)
insert into client_accounts (client_id, accounts_id) values (2, 2)
insert into account (balance, overdraft, dtype, id) values (3000, 0, 'CheckingAccount', 3)
insert into client_accounts (client_id, accounts_id) values (2, 3)
update client set active_account_id = 2 where id = 2;

insert into client (city, gender, name, id) values ('Moscow', 0, 'Jonny Bravo', 3)
insert into account (balance, dtype, id) values (1000, 'SavingAccount', 4)
insert into client_accounts (client_id, accounts_id) values (3, 4)
insert into account (balance, overdraft, dtype, id) values (4000, 1000, 'CheckingAccount', 5)
insert into client_accounts (client_id, accounts_id) values (3, 5)
update client set active_account_id = 4 where id = 3;
