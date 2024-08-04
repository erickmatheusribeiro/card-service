CREATE TABLE IF NOT EXISTS card
  (
     id               SERIAL NOT NULL,
     cpf              VARCHAR(255) NOT NULL,
     cardnumber       VARCHAR(255) NOT NULL,
     validation       VARCHAR(6) NOT NULL,
     cvv              VARCHAR(3) NOT NULL,
     datetimecreation TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
     PRIMARY KEY (id)
  );

CREATE TABLE IF NOT EXISTS card_limit
  (
     id               SERIAL NOT NULL,
     cardid           BIGINT NOT NULL,
     limite           DOUBLE PRECISION NOT NULL,
     updated          TIMESTAMP NOT NULL,
     PRIMARY KEY (id)
  );

CREATE TABLE IF NOT EXISTS card_transaction
  (
     id               SERIAL NOT NULL,
     cardid           BIGINT NOT NULL,
     paymentid        VARCHAR(255) NOT NULL,
     value            DOUBLE PRECISION NOT NULL,
     datetimecreation TIMESTAMP NOT NULL,
     PRIMARY KEY (id)
  );

ALTER TABLE card_transaction
  ADD CONSTRAINT fk_card_transaction_card
  FOREIGN KEY (cardid)
  REFERENCES card (id);

ALTER TABLE card_limit
  ADD CONSTRAINT fk_card_limit_card
  FOREIGN KEY (cardid)
  REFERENCES card (id);