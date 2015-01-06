package com.pmrodrigues.ellasa.repositories.impl;

import com.pmrodrigues.ellasa.models.Produto;
import com.pmrodrigues.ellasa.repositories.ProdutoRepository;
import org.springframework.stereotype.Repository;

@Repository("ProdutoRepository")
public class ProdutoRepositoryImpl extends AbstractRepository<Produto>
		implements
			ProdutoRepository {

	private static final long serialVersionUID = 1L;

}
