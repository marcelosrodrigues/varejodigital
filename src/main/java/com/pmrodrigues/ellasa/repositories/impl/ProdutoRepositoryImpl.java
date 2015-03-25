package com.pmrodrigues.ellasa.repositories.impl;

import com.pmrodrigues.ellasa.models.Loja;
import com.pmrodrigues.ellasa.models.Produto;
import com.pmrodrigues.ellasa.repositories.ProdutoRepository;
import com.pmrodrigues.ellasa.repositories.ResultList;
import org.apache.commons.validator.GenericValidator;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.MatchMode;
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
    public List<Produto> listByLoja(final Loja loja) {
        return this.getSession()
                .createCriteria(Produto.class, "produto")
                .createCriteria("produto.imagens", "imagem", JoinType.LEFT_OUTER_JOIN)
                .add(Restrictions.eq("produto.loja", loja))
                .addOrder(Order.asc("produto.id"))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .setFetchMode("produto.imagens", FetchMode.JOIN)
                .list();
    }

    @Override
    public ResultList<Produto> search(final Produto produto) {
        return this.search(produto, 0);
    }

    @Override
    public ResultList<Produto> search(final Produto produto, final Integer page) {

        final Criteria criteria = this.getSession().createCriteria(Produto.class, "p")
                .addOrder(Order.asc("p.id"));

        if (produto != null) {
            if (produto.getLoja() != null && produto.getLoja().getId() > 0L) {
                criteria.add(Restrictions.eq("p.loja", produto.getLoja()));
            }
            if (produto.getSecao() != null && produto.getSecao().getId() > 0L) {
                criteria.add(Restrictions.eq("p.secao", produto.getSecao()));
            }
            if (!GenericValidator.isBlankOrNull(produto.getNome())) {
                criteria.add(Restrictions.ilike("p.nome", produto.getNome(), MatchMode.START));
            }
        }

        return new ResultList<>(criteria, page);

    }
}
