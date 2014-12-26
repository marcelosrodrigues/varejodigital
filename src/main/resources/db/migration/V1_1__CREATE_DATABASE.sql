CREATE TABLE contrato (
  id            BIGINT NOT NULL AUTO_INCREMENT,
  inicio        DATE   NOT NULL,
  termino       DATE,
  franqueado_id BIGINT NOT NULL,
  contrato_id   BIGINT NOT NULL,
  PRIMARY KEY (id)
);

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
  method    INTEGER,
  PRIMARY KEY (id)
);

CREATE TABLE ordempagamento (
  id               BIGINT         NOT NULL AUTO_INCREMENT,
  carrinho         VARCHAR(255)   NOT NULL,
  codigo           VARCHAR(255),
  dataEnvio        DATETIME,
  dataGeracao      DATETIME,
  descricao        VARCHAR(255)   NOT NULL,
  documento        VARCHAR(255),
  motivo           VARCHAR(255),
  status           VARCHAR(255),
  valor            DECIMAL(19, 2) NOT NULL,
  contrato_id      BIGINT         NOT NULL,
  meiopagamento_id BIGINT         NOT NULL,
  pedido_id        BIGINT,
  PRIMARY KEY (id)
);

CREATE TABLE ordempagamentocartaocredito (
  codigosegura  VARCHAR(3)   NOT NULL,
  cpf           VARCHAR(255) NOT NULL,
  dataExpiracao DATE         NOT NULL,
  numero        VARCHAR(255) NOT NULL,
  portador      VARCHAR(255) NOT NULL,
  telefone      VARCHAR(255) NOT NULL,
  id            BIGINT       NOT NULL,
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

CREATE TABLE tipofranquia (
  id        BIGINT         NOT NULL AUTO_INCREMENT,
  descricao VARCHAR(255)   NOT NULL,
  nome      VARCHAR(255)   NOT NULL,
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

CREATE TABLE cep (
  id        BIGINT NOT NULL AUTO_INCREMENT,
  final     BIGINT NOT NULL,
  inicial   BIGINT NOT NULL,
  estado_id BIGINT NOT NULL,
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

CREATE TABLE comissao (
  item_id       BIGINT NOT NULL,
  taxa_id       BIGINT NOT NULL,
  valorTotal    DECIMAL(19, 2),
  valorUnitario DECIMAL(19, 2),
  PRIMARY KEY (item_id, taxa_id)
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

CREATE TABLE faixa_preco (
  id         BIGINT         NOT NULL AUTO_INCREMENT,
  preco      DECIMAL(19, 2) NOT NULL,
  uf_destino BIGINT         NOT NULL,
  uf_origem  BIGINT         NOT NULL,
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

CREATE TABLE taxa (
  id    BIGINT NOT NULL AUTO_INCREMENT,
  nome  VARCHAR(255),
  valor DECIMAL(19, 2),
  PRIMARY KEY (id)
);

ALTER TABLE estado ADD CONSTRAINT UF_ESTADO UNIQUE (uf);

ALTER TABLE usuario ADD CONSTRAINT UK_CPF UNIQUE (cpf);

ALTER TABLE usuario ADD CONSTRAINT UK_EMAIL UNIQUE (email);

ALTER TABLE perfil ADD CONSTRAINT UK_NOME UNIQUE (nome);

ALTER TABLE contrato ADD CONSTRAINT FK_FRANQUEADO FOREIGN KEY (franqueado_id) REFERENCES usuario (id);

ALTER TABLE contrato ADD CONSTRAINT FK_TIPO_FRANQUIA FOREIGN KEY (contrato_id) REFERENCES tipofranquia (id);

ALTER TABLE imagem ADD CONSTRAINT FK_PRODUTO_IMAGEM FOREIGN KEY (produto_id) REFERENCES produto (id);

ALTER TABLE ordempagamento ADD CONSTRAINT FK_CONTRATO FOREIGN KEY (contrato_id) REFERENCES contrato (id);

ALTER TABLE ordempagamento ADD CONSTRAINT FK_MEIO_DE_PAGAMENTO FOREIGN KEY (meiopagamento_id) REFERENCES meiopagamento (id);

ALTER TABLE ordempagamento ADD CONSTRAINT FK_PEDIDO FOREIGN KEY (pedido_id) REFERENCES pedido (id);

ALTER TABLE ordempagamentocartaocredito ADD CONSTRAINT PK_ORDEM_PAGAMENTO FOREIGN KEY (id) REFERENCES ordempagamento (id);

ALTER TABLE pedido ADD CONSTRAINT FK_CLIENTE FOREIGN KEY (cliente_id) REFERENCES cliente (id);

ALTER TABLE pedido ADD CONSTRAINT FK_ENDERECO_ENTREGA FOREIGN KEY (enderecoentrega_id) REFERENCES endereco (id);

ALTER TABLE pedido ADD CONSTRAINT FK_LOJA FOREIGN KEY (loja_id) REFERENCES loja (id);

ALTER TABLE pedido ADD CONSTRAINT FK_VENDEDOR FOREIGN KEY (vendedor_id) REFERENCES usuario (id);

ALTER TABLE usuario ADD CONSTRAINT FK_CELULAR FOREIGN KEY (celular_id) REFERENCES telefone (id);

ALTER TABLE usuario ADD CONSTRAINT FK_USUARIO_ESTADO FOREIGN KEY (estado_id) REFERENCES estado (id);

ALTER TABLE usuario ADD CONSTRAINT FK_RESIDENCIAL FOREIGN KEY (residencial_id) REFERENCES telefone (id);

ALTER TABLE atributo_produto ADD CONSTRAINT FK_ATRIBUTO_PRODUTO FOREIGN KEY (produto_id) REFERENCES produto (id);

ALTER TABLE cep ADD CONSTRAINT FK_ESTADO_CEP FOREIGN KEY (estado_id) REFERENCES estado (id);

ALTER TABLE comissao ADD CONSTRAINT FK_TAXA FOREIGN KEY (taxa_id) REFERENCES taxa (id);

ALTER TABLE comissao ADD CONSTRAINT PK_ITEM_PEDIDO FOREIGN KEY (item_id) REFERENCES item_pedido (id);

ALTER TABLE endereco ADD CONSTRAINT FK_ENDERECO_CLIENTE FOREIGN KEY (cliente_id) REFERENCES cliente (id);

ALTER TABLE endereco ADD CONSTRAINT FK_ENDERECO_ESTADO FOREIGN KEY (estado_id) REFERENCES estado (id);

ALTER TABLE faixa_preco ADD CONSTRAINT FK_ESTADO_DESTINO FOREIGN KEY (uf_destino) REFERENCES estado (id);

ALTER TABLE faixa_preco ADD CONSTRAINT FK_ESTADO_ORIGEM FOREIGN KEY (uf_origem) REFERENCES estado (id);

ALTER TABLE item_pedido ADD CONSTRAINT FK_ATRIBUTO_ITEM_PEDIDO FOREIGN KEY (atributo_id) REFERENCES atributo_produto (id);

ALTER TABLE item_pedido ADD CONSTRAINT FK_PRODUTO_PEDIDO FOREIGN KEY (produto_id) REFERENCES produto (id);

ALTER TABLE item_pedido  ADD CONSTRAINT FK_ITEM_PEDIDO_PEDIDO FOREIGN KEY (pedido_id) REFERENCES pedido (id);

ALTER TABLE membros ADD CONSTRAINT FK_PERFIL  FOREIGN KEY (perfil_id) REFERENCES perfil (id);

ALTER TABLE membros  ADD CONSTRAINT FK_USUARIO FOREIGN KEY (usuario_id) REFERENCES usuario (id);

ALTER TABLE produto  ADD CONSTRAINT FK_PRODUTO_LOJA  FOREIGN KEY (loja_id) REFERENCES loja (id);

ALTER TABLE produto ADD CONSTRAINT FK_SECAO_PRODUTO FOREIGN KEY (secao_id) REFERENCES secao (id);

ALTER TABLE secao ADD CONSTRAINT FK_SECAO_PAI FOREIGN KEY (pai_id) REFERENCES secao (id);
