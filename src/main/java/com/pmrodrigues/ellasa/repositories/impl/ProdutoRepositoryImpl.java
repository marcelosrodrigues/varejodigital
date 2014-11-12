package com.pmrodrigues.ellasa.repositories.impl;

import com.pmrodrigues.ellasa.models.Loja;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.pmrodrigues.ellasa.models.Produto;
import com.pmrodrigues.ellasa.repositories.ProdutoRepository;

import java.util.List;

@Repository("ProdutoRepository")
public class ProdutoRepositoryImpl extends AbstractRepository<Produto>
		implements
			ProdutoRepository {

	private static final long serialVersionUID = 1L;

    @Override
    public List<Produto> listByLoja(final Loja loja) {
        return this.getSession().createCriteria(Produto.class,"p")
                   .add(Restrictions.eq("loja",loja))
                   .addOrder(Order.asc("secao.id"))
                   .addOrder(Order.asc("nome"))
                   .list();
    }
}
