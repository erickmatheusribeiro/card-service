insert into card (cpf, cardnumber, validation, cvv) values ('57386600054', '5100643631709388', '0625', '145');
insert into card (cpf, cardnumber, validation, cvv) values ('47792626903', '3726388357554552', '0932', '245');
insert into card (cpf, cardnumber, validation, cvv) values ('13838303040', '6062828688223656', '0528', '345');
insert into card (cpf, cardnumber, validation, cvv) values ('01635070074', '3546502468307657', '0227', '445');

insert into card_limit (cardid, limite, updated) values (1, 1000.0, now());
insert into card_limit (cardid, limite, updated) values (2, 1000.0, now());
insert into card_limit (cardid, limite, updated) values (3, 1000.0, now());
insert into card_limit (cardid, limite, updated) values (4, 1000.0, now());
