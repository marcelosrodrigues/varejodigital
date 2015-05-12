ALTER TABLE meiopagamento ADD COLUMN tipo CHAR(1) NULL;

INSERT INTO meiopagamento (id, descricao, tipo) VALUES
  (1, 'Boleto Bancário', 'B'),
  (2, 'Cartão Visa', 'C'),
  (3, 'Cartão Master', 'C'),
  (4, 'Cartão Amex', 'C'),
  (5, 'Cartão Elo', 'C'),
  (6, 'Cartão Diners', 'C'),
  (7, 'TEF - Itaú', 'T'),
  (8, 'TEF - Bradesco', 'T');
