CREATE TABLE lojistas (
  usuario_id BIGINT NOT NULL,
  loja_id    BIGINT NOT NULL,
  PRIMARY KEY (usuario_id, loja_id)
);

ALTER TABLE lojistas ADD CONSTRAINT FK_LOJAS_USUARIO FOREIGN KEY (loja_id) REFERENCES loja (id);

ALTER TABLE lojistas  ADD CONSTRAINT FK_USUARIOS_LOJA FOREIGN KEY (usuario_id) REFERENCES usuario (id);