ALTER TABLE meiopagamento ADD COLUMN tipo CHAR(1) NULL;

INSERT INTO meiopagamento (id, descricao, tipo, method) VALUES
(1, 'Boleto Bancário', 'B', 0),
(2, 'Cartão Visa', 'C', 3),
(3, 'Cartão Master', 'C', 4),
(4, 'Cartão Amex', 'C', 4),
(5, 'Cartão Elo', 'C', 6),
(6, 'Cartão Diners', 'C', 7),
(7, 'TEF - Itaú', 'T', 1),
(8, 'TEF - Bradesco', 'T', 2);
