package com.pmrodrigues.ellasa.repositories.impl;

import com.pmrodrigues.ellasa.models.Secao;
import com.pmrodrigues.ellasa.repositories.ResultList;
import com.pmrodrigues.ellasa.repositories.SecaoRepository;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class SecaoRepositoryImpl extends AbstractRepository<Secao>
		implements
			SecaoRepository {

	/**
	 * 
	 */
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
}
