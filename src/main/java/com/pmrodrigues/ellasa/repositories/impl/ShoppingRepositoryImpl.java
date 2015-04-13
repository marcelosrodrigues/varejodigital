package com.pmrodrigues.ellasa.repositories.impl;

import com.pmrodrigues.ellasa.models.Loja;
import com.pmrodrigues.ellasa.repositories.ResultList;
import com.pmrodrigues.ellasa.repositories.ShoppingRepository;
import org.apache.commons.validator.GenericValidator;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Marceloo on 12/11/2014.
 */
@Repository("ShoppingRepository")
public class ShoppingRepositoryImpl extends AbstractRepository<Loja> implements ShoppingRepository {

    public ResultList<Loja> search(final Loja loja, final Integer page){
        final Criteria criteria = this.getSession()
                                       .createCriteria(Loja.class)
                                       .addOrder(Order.asc("nome"));

        if( loja != null && !GenericValidator.isBlankOrNull(loja.getNome())) {
           criteria.add(Restrictions.like("nome", loja.getNome() + "%", MatchMode.END));
        }

        return new ResultList<Loja>(criteria,page);
    }

    @Override
    public List<Loja> listByNome(String nome) {
        return this.getSession().createCriteria(Loja.class)
                .add(Restrictions.like("nome", nome, MatchMode.START))
                .addOrder(Order.asc("nome"))
                .list();
    }
}
