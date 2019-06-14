--Possible energy trade options
insert into material(id, name, price) values (1, 'gas', 8.0);
insert into material(id, name, price) values (2, 'coal', 4.0);
insert into material(id, name, price) values (3, 'sun', 10.0);
insert into material(id, name, price) values (4, 'wind', 12.0);

--Contract fundamentally is aggreement between two companies and amount of material traded
insert into contract(a_company, another_company, material_id, amount, status, transaction_date) values ('company_1', 'company_2', 1, 3000.0, true, {ts '2015-05-17 18:47:52.69'});
insert into contract(a_company, another_company, material_id, amount, status, transaction_date) values ('company_1', 'company_3', 4, 8000.0, true, {ts '2019-05-04 18:47:52.69'});
insert into contract(a_company, another_company, material_id, amount, status, transaction_date) values ('company_4', 'company_1', 3, 12000.0, true, {ts '2019-01-01 18:47:52.69'});
insert into contract(a_company, another_company, material_id, amount, status, transaction_date) values ('company_5', 'company_1', 2, 12000.0, true, {ts '2019-05-22 18:47:52.69'});
insert into contract(a_company, another_company, material_id, amount, status, transaction_date) values ('company_5', 'company_4', 1, 10000.0, true, {ts '2019-05-20 18:47:52.69'});
insert into contract(a_company, another_company, material_id, amount, status, transaction_date) values ('company_6', 'company_2', 2, 8000.0, true, {ts '2019-05-14 18:47:52.69'});
insert into contract(a_company, another_company, material_id, amount, status, transaction_date) values ('company_2', 'company_7', 1, 10000.0, true, {ts '2011-01-11 18:47:52.69'});
insert into contract(a_company, another_company, material_id, amount, status, transaction_date) values ('company_6', 'company_7', 3, 8000.0, true, {ts '2019-05-12 18:47:52.69'});
insert into contract(a_company, another_company, material_id, amount, status, transaction_date) values ('company_7', 'company_8', 4, 8000.0, true, {ts '2018-05-10 18:47:52.69'});
insert into contract(a_company, another_company, material_id, amount, status, transaction_date) values ('company_6', 'company_8', 2, 15000.0, true, {ts '2017-01-05 18:47:52.69'});
