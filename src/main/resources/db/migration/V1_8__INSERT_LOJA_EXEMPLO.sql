ALTER TABLE areas_vendas DROP FOREIGN KEY FK_LOJA_AREA_VENDA;

ALTER TABLE areas_vendas ADD CONSTRAINT FK_LOJA_AREA_VENDA FOREIGN KEY (produto_id) REFERENCES LOJA (id);

insert into loja (id, nome) values ( 1, 'PROJETANDOO');

insert into areas_vendas (  produto_id, secao_id )
  select 1 , id from secao;
