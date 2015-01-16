package com.pmrodrigues.ellasa.repositories.impl;

import com.pmrodrigues.ellasa.models.Loja;
import com.pmrodrigues.ellasa.models.Produto;
import com.pmrodrigues.ellasa.repositories.ProdutoRepository;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ProdutoRepository")
public class ProdutoRepositoryImpl extends AbstractRepository<Produto>
        implements
        ProdutoRepository {

    private static final long serialVersionUID = 1L;

    @Override
    public List<Produto> listByLoja(Loja loja) {
        return this.getSession()
                .createCriteria(Produto.class, "produto")
                .createCriteria("produto.imagens", "imagem", JoinType.LEFT_OUTER_JOIN)
                .add(Restrictions.eq("produto.loja", loja))
                .addOrder(Order.asc("produto.id"))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .setFetchMode("produto.imagens", FetchMode.JOIN)
                .list();
    }
}
