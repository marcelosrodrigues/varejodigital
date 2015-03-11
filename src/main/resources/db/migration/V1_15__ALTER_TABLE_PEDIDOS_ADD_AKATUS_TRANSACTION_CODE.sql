alter table pedido
    add column akatus_transaction_code varchar(200) null ,
    add column motivo varchar(200) null ,
    add column data_aprovacao timestamp null;