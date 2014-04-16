package com.pmrodrigues.ellasa.repositories.impl;

import org.springframework.stereotype.Repository;

import com.pmrodrigues.ellasa.models.Produto;
import com.pmrodrigues.ellasa.repositories.ProdutoRepository;

@Repository
public class ProdutoRepositoryImpl extends AbstractRepository<Produto>
		implements
			ProdutoRepository {

	private static final long serialVersionUID = 1L;

}
