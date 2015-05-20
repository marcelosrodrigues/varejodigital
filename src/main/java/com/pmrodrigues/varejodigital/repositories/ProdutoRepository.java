package com.pmrodrigues.varejodigital.repositories;

import com.pmrodrigues.varejodigital.models.Loja;
import com.pmrodrigues.varejodigital.models.Produto;

import java.util.List;

public interface ProdutoRepository extends Repository<Produto> {
    List<Produto> listByLoja(Loja loja);

    ResultList<Produto> search(Produto produto);

    ResultList<Produto> search(Produto produto, Integer page);

    Produto findByCodigoProduto(Long codigo);
}
