package com.pmrodrigues.ellasa.repositories.impl;

import com.pmrodrigues.ellasa.models.Loja;
import com.pmrodrigues.ellasa.models.Secao;
import com.pmrodrigues.ellasa.repositories.ResultList;
import com.pmrodrigues.ellasa.repositories.SecaoRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SecaoRepositoryImpl extends AbstractRepository<Secao>
		implements
			SecaoRepository {

	private static final long serialVersionUID = 1L;

    @Override
    public ResultList<Secao> search(final Secao secao) {
        return new ResultList<>(this.getSession()
                                         .createCriteria(Secao.class, "s")
                                         .add(Restrictions.isNull("s.pai"))
                                         .addOrder(Order.asc("s.nome")));
    }

    @Override
    public List<Secao> listAll() {
        return this.getSession().createCriteria(Secao.class,"s")
                                .addOrder(Order.asc("s.nome"))
                                .list();
    }

    @Override
    public List<Secao> findByLoja(final Loja loja) {
        return this.getSession().createCriteria(Secao.class, "s")
                .createCriteria("s.subsecoes", "subsecoes", JoinType.INNER_JOIN)
                .createCriteria("s.lojas", "loja", JoinType.INNER_JOIN)
                .add(Restrictions.eq("loja.id", loja.getId()))
                .add(Restrictions.isNull("s.pai"))
                .addOrder(Order.asc("s.id"))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }
}
