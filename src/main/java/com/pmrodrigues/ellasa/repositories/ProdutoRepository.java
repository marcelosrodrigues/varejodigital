package com.pmrodrigues.ellasa.repositories;

import com.pmrodrigues.ellasa.models.Loja;
import com.pmrodrigues.ellasa.models.Produto;

import java.util.List;

public interface ProdutoRepository extends Repository<Produto> {
    List<Produto> listByLoja(Loja loja);
}
