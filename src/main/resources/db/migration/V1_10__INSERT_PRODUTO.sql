ALTER TABLE produto
CHANGE COLUMN descricao descricao VARCHAR(6000) NULL,
CHANGE COLUMN descricao_curta descricao_curta VARCHAR(6000) NULL DEFAULT NULL;
