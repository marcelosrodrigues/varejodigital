package com.pmrodrigues.ellasa.repositories.impl;

import com.pmrodrigues.ellasa.models.Loja;
import com.pmrodrigues.ellasa.models.Secao;
import com.pmrodrigues.ellasa.repositories.ResultList;
import com.pmrodrigues.ellasa.repositories.SecaoRepository;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.MatchMode;
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
                .addOrder(Order.asc("s.nome"))
                .setFetchMode("subsecoes", FetchMode.JOIN));
    }

    @Override
    public List<Secao> listAll() {
        return this.getSession().createCriteria(Secao.class, "s")
                .addOrder(Order.asc("s.nome"))
                .list();
    }

    @Override
    public List<Secao> findByLoja(final Loja loja) {
        return this.getSession().createCriteria(Secao.class, "s")
                .createCriteria("s.pai", "pai", JoinType.LEFT_OUTER_JOIN)
                .createCriteria("s.lojas", "loja", JoinType.INNER_JOIN)
                .add(Restrictions.eq("loja.id", loja.getId()))
                .addOrder(Order.asc("s.id"))
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .list();
    }

    @Override
    public List<Secao> listByNome(Loja loja, final String nome) {
        return this.getSession().createCriteria(Secao.class, "s")
                .createAlias("s.lojas", "loja", JoinType.INNER_JOIN)
                .add(Restrictions.eq("loja.id", loja.getId()))
                .add(Restrictions.like("s.nome", nome, MatchMode.START))
                .addOrder(Order.asc("s.id"))
                .list();
    }

    @Override
    public List<Secao> listAllSubSecoesByLojaAndPai(final Loja loja, final Secao pai) {
        return this.getSession().createCriteria(Secao.class, "s")
                .createAlias("s.lojas", "loja", JoinType.INNER_JOIN)
                .add(Restrictions.eq("loja.id", loja.getId()))
                .add(Restrictions.eq("s.pai", pai))
                .addOrder(Order.asc("s.id"))
                .list();


    }
}
