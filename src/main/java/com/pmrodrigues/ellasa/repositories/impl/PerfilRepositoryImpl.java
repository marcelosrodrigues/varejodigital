package com.pmrodrigues.ellasa.repositories.impl;

import com.pmrodrigues.ellasa.models.Perfil;
import com.pmrodrigues.ellasa.repositories.PerfilRepository;
import com.pmrodrigues.ellasa.repositories.ResultList;
import org.apache.commons.validator.GenericValidator;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * Created by Marceloo on 18/12/2014.
 */
@Repository("PerfilRepository")
public class PerfilRepositoryImpl extends AbstractRepository<Perfil> implements PerfilRepository {

    @Override
    public ResultList<Perfil> search(final Perfil perfil, final Integer page) {

        final Criteria criteria = this.getSession().createCriteria(Perfil.class)
                .addOrder(Order.asc("id"));

        if (perfil != null && !GenericValidator.isBlankOrNull(perfil.getNome())) {
            criteria.add(Restrictions.like("nome", perfil.getNome(), MatchMode.START));
        }

        return new ResultList<>(criteria, page);


    }
}
