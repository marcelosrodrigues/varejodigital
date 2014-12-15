package com.pmrodrigues.ellasa.repositories.impl;

import static java.lang.String.format;

import com.pmrodrigues.ellasa.exceptions.UniqueException;
import com.pmrodrigues.ellasa.repositories.ResultList;
import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pmrodrigues.ellasa.models.Usuario;
import com.pmrodrigues.ellasa.repositories.UsuarioRepository;

@Repository("UsuarioRepository")
public class UsuarioRepositoryImpl extends AbstractRepository<Usuario> implements UsuarioRepository {

	private static final long serialVersionUID = 1L;

	private static final Logger LOGGER = Logger
			.getLogger(UsuarioRepositoryImpl.class);

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public Usuario findByEmail(final String email) {

		LOGGER.debug(format("Pesquisando usuário pelo email %s", email));
		
		final Usuario usuario = (Usuario) super.getSession()
				.getNamedQuery("Usuario.FindByEmail")
				.setParameter("email", email)
				.uniqueResult();

		LOGGER.debug(format("Usuario %s encontrado", usuario));

		return usuario;
	}

    @Override
    public void add(final Usuario usuario) {

        Long counted = (Long) this.getSession().createCriteria(Usuario.class)
                                .add(Restrictions.or(Restrictions.eq("email", usuario.getEmail()),
                                     Restrictions.eq("cpf", usuario.getCpf())))
                                .setProjection(Projections.rowCount())
                                .uniqueResult();
        if( counted == 0L ) {
            super.add(usuario);
        } else {
            throw new UniqueException(format("Não foi possível incluir o usuario %s pois já existe outro com o email %s ou com o CPF %s",usuario.getNomeCompleto(),usuario.getEmail(),usuario.getCpf()));
        }



    }

    public ResultList<Usuario> search(final Usuario usuario, final Integer page) {
        Criteria criteria = this.getSession().createCriteria(Usuario.class)
                                .addOrder(Order.asc("id"));

        if( usuario != null ){
            if(!GenericValidator.isBlankOrNull(usuario.getEmail())){
                criteria.add(Restrictions.eq("email",usuario.getEmail()));
            }
        }

        return new ResultList<Usuario>(criteria,page);
    }
}
