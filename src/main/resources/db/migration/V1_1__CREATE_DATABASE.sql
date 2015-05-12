CREATE TABLE estado (
  id   BIGINT       NOT NULL,
  nome VARCHAR(255) NOT NULL,
  uf   CHAR(2)      NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE imagem (
  id         BIGINT NOT NULL,
  image      VARCHAR(255),
  produto_id BIGINT,
  PRIMARY KEY (id)
);

CREATE TABLE loja (
  id   BIGINT       NOT NULL AUTO_INCREMENT,
  nome VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE meiopagamento (
  id        BIGINT NOT NULL AUTO_INCREMENT,
  descricao VARCHAR(255),
  PRIMARY KEY (id)
);

CREATE TABLE pedido (
  id                 BIGINT NOT NULL AUTO_INCREMENT,
  codigoTransacao    VARCHAR(255),
  dataAlteracao      DATETIME,
  dataCompra         DATETIME,
  dataCriacaco       DATETIME,
  dataEntrega        DATETIME,
  status             INTEGER,
  cliente_id         BIGINT NOT NULL,
  enderecoentrega_id BIGINT NOT NULL,
  loja_id            BIGINT NOT NULL,
  vendedor_id        BIGINT NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE telefone (
  id       BIGINT       NOT NULL AUTO_INCREMENT,
  ddd      VARCHAR(255) NOT NULL,
  telefone VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE usuario (
  id             BIGINT       NOT NULL AUTO_INCREMENT,
  bloqueado      BIT,
  cpf            VARCHAR(255) NOT NULL,
  dataNascimento DATE         NOT NULL,
  email          VARCHAR(255) NOT NULL,
  bairro         VARCHAR(255) NOT NULL,
  cep            VARCHAR(255) NOT NULL,
  cidade         VARCHAR(255) NOT NULL,
  complemento    VARCHAR(255),
  logradouro     VARCHAR(255) NOT NULL,
  numero         VARCHAR(255) NOT NULL,
  nomeCompleto   VARCHAR(255) NOT NULL,
  password       VARCHAR(255),
  celular_id     BIGINT,
  estado_id      BIGINT       NOT NULL,
  residencial_id BIGINT,
  PRIMARY KEY (id)
);

CREATE TABLE atributo_produto (
  id         BIGINT NOT NULL AUTO_INCREMENT,
  descricao  VARCHAR(255),
  produto_id BIGINT NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE cliente (
  id             BIGINT NOT NULL AUTO_INCREMENT,
  dataAlteracao  DATETIME,
  dataCriacaco   DATETIME,
  dataNascimento DATE,
  email          VARCHAR(255),
  primeiroNome   VARCHAR(255),
  ultimoNome     VARCHAR(255),
  PRIMARY KEY (id)
);

CREATE TABLE endereco (
  id            BIGINT NOT NULL AUTO_INCREMENT,
  bairro        VARCHAR(255),
  celular       VARCHAR(255),
  cep           VARCHAR(255),
  cidade        VARCHAR(255),
  dataAlteracao DATETIME,
  dataCriacaco  DATETIME,
  logradouro    VARCHAR(255),
  telefone      VARCHAR(255),
  cliente_id    BIGINT NOT NULL,
  estado_id     BIGINT NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE item_pedido (
  id          BIGINT NOT NULL AUTO_INCREMENT,
  preco       DECIMAL(19, 2) NOT NULL,
  quantidade  BIGINT NOT NULL,
  atributo_id BIGINT,
  produto_id  BIGINT NOT NULL,
  pedido_id   BIGINT NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE membros (
  usuario_id BIGINT NOT NULL,
  perfil_id  BIGINT NOT NULL,
  PRIMARY KEY (usuario_id, perfil_id)
);

CREATE TABLE perfil (
  id   BIGINT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(255),
  PRIMARY KEY (id)
);

CREATE TABLE produto (
  id              BIGINT NOT NULL AUTO_INCREMENT,
  descricao       VARCHAR(255),
  descricao_curta VARCHAR(255),
  nome            VARCHAR(255),
  peso            DECIMAL(19, 2),
  preco           DECIMAL(19, 2),
  loja_id         BIGINT NOT NULL,
  secao_id        BIGINT,
  PRIMARY KEY (id)
);

CREATE TABLE secao (
  id     BIGINT NOT NULL AUTO_INCREMENT,
  secao  VARCHAR(255),
  pai_id BIGINT,
  PRIMARY KEY (id)
);

ALTER TABLE estado ADD CONSTRAINT UF_ESTADO UNIQUE (uf);

ALTER TABLE usuario ADD CONSTRAINT UK_CPF UNIQUE (cpf);

ALTER TABLE usuario ADD CONSTRAINT UK_EMAIL UNIQUE (email);

ALTER TABLE perfil ADD CONSTRAINT UK_NOME UNIQUE (nome);

ALTER TABLE imagem ADD CONSTRAINT FK_PRODUTO_IMAGEM FOREIGN KEY (produto_id) REFERENCES produto (id);

ALTER TABLE pedido ADD CONSTRAINT FK_CLIENTE FOREIGN KEY (cliente_id) REFERENCES cliente (id);

ALTER TABLE pedido ADD CONSTRAINT FK_ENDERECO_ENTREGA FOREIGN KEY (enderecoentrega_id) REFERENCES endereco (id);

ALTER TABLE pedido ADD CONSTRAINT FK_LOJA FOREIGN KEY (loja_id) REFERENCES loja (id);

ALTER TABLE pedido ADD CONSTRAINT FK_VENDEDOR FOREIGN KEY (vendedor_id) REFERENCES usuario (id);

ALTER TABLE usuario ADD CONSTRAINT FK_CELULAR FOREIGN KEY (celular_id) REFERENCES telefone (id);

ALTER TABLE usuario ADD CONSTRAINT FK_USUARIO_ESTADO FOREIGN KEY (estado_id) REFERENCES estado (id);

ALTER TABLE usuario ADD CONSTRAINT FK_RESIDENCIAL FOREIGN KEY (residencial_id) REFERENCES telefone (id);

ALTER TABLE atributo_produto ADD CONSTRAINT FK_ATRIBUTO_PRODUTO FOREIGN KEY (produto_id) REFERENCES produto (id);

ALTER TABLE endereco ADD CONSTRAINT FK_ENDERECO_CLIENTE FOREIGN KEY (cliente_id) REFERENCES cliente (id);

ALTER TABLE endereco ADD CONSTRAINT FK_ENDERECO_ESTADO FOREIGN KEY (estado_id) REFERENCES estado (id);

ALTER TABLE item_pedido ADD CONSTRAINT FK_ATRIBUTO_ITEM_PEDIDO FOREIGN KEY (atributo_id) REFERENCES atributo_produto (id);

ALTER TABLE item_pedido ADD CONSTRAINT FK_PRODUTO_PEDIDO FOREIGN KEY (produto_id) REFERENCES produto (id);

ALTER TABLE item_pedido  ADD CONSTRAINT FK_ITEM_PEDIDO_PEDIDO FOREIGN KEY (pedido_id) REFERENCES pedido (id);

ALTER TABLE membros ADD CONSTRAINT FK_PERFIL  FOREIGN KEY (perfil_id) REFERENCES perfil (id);

ALTER TABLE membros  ADD CONSTRAINT FK_USUARIO FOREIGN KEY (usuario_id) REFERENCES usuario (id);

ALTER TABLE produto  ADD CONSTRAINT FK_PRODUTO_LOJA  FOREIGN KEY (loja_id) REFERENCES loja (id);

ALTER TABLE produto ADD CONSTRAINT FK_SECAO_PRODUTO FOREIGN KEY (secao_id) REFERENCES secao (id);

ALTER TABLE secao ADD CONSTRAINT FK_SECAO_PAI FOREIGN KEY (pai_id) REFERENCES secao (id);
