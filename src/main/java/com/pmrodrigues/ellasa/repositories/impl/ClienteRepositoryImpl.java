package com.pmrodrigues.ellasa.repositories.impl;

import com.pmrodrigues.ellasa.enumarations.Tipo;
import com.pmrodrigues.ellasa.models.Cliente;
import com.pmrodrigues.ellasa.repositories.ClienteRepository;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

@Repository("ClienteRepository")
public class ClienteRepositoryImpl extends AbstractRepository<Cliente> implements ClienteRepository {

    private static final long serialVersionUID = 1L;

    @Override
    public Cliente findByEmail(final String email) {
        return (Cliente) this.getSession().createCriteria(Cliente.class)
                .createAlias("endereco", "e", JoinType.INNER_JOIN)
                .createAlias("e.estado", "ee", JoinType.INNER_JOIN)
                .add(Restrictions.eq("e.tipo", Tipo.RESIDENCIAL))
                .add(Restrictions.eq("email", email))
                .uniqueResult();
    }
}
