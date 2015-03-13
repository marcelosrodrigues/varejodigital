CREATE TABLE areas_vendas (
    produto_id BIGINT NOT NULL,
    secao_id BIGINT NOT NULL,
    primary key (produto_id , secao_id)
);

ALTER TABLE areas_vendas ADD CONSTRAINT FK_LOJA_AREA_VENDA FOREIGN KEY (produto_id) REFERENCES loja (id);

ALTER TABLE areas_vendas ADD CONSTRAINT FK_SECAO_LOJA_ATENDIDA FOREIGN KEY (secao_id) REFERENCES secao (id);