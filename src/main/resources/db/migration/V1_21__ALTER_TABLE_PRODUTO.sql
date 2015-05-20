alter table produto
      add preco_custo DECIMAL(19, 2) null,
      add codigo_barra VARCHAR(20) null,
      add codigo_interno BIGINT null,
      add codigo_externo BIGINT null,
      add quantidade_max_estoque BIGINT null,
      add quantidade_min_estoque BIGINT null,
      add quantidade_em_estoque BIGINT null,
      add ponto_de_ressuprimento BIGINT null,
      add ponto_de_reposicao BIGINT null;