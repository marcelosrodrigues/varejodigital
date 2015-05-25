alter table pedido
    MODIFY cliente_id BIGINT null;

alter table pedido
    MODIFY enderecoentrega_id BIGINT null;

ALTER TABLE pedido
    MODIFY vendedor_id BIGINT null;