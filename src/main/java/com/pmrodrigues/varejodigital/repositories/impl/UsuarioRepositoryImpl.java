package com.pmrodrigues.varejodigital.repositories.impl;

import com.pmrodrigues.varejodigital.exceptions.UniqueException;
import com.pmrodrigues.varejodigital.models.Usuario;
import com.pmrodrigues.varejodigital.repositories.ResultList;
import com.pmrodrigues.varejodigital.repositories.UsuarioRepository;
import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

import static java.lang.String.format;

@Repository("UsuarioRepository")
public class UsuarioRepositoryImpl extends AbstractRepository<Usuario> implements UsuarioRepository {

    private static final long serialVersionUID = 1L;

    private static final Logger LOGGER = Logger
            .getLogger(UsuarioRepositoryImpl.class);

    @Override
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
    public List<Usuario> listByNomeOrEmail(final String nome) {
        return this.getSession().createCriteria(Usuario.class)
                .add(Restrictions.or(Restrictions.like("nomeCompleto", nome, MatchMode.START),
                        Restrictions.like("email", nome, MatchMode.START)))
                .list();
    }

    @Override
    public void add(final Usuario usuario) {

        final Long counted = (Long) this.getSession().createCriteria(Usuario.class)
                .add(Restrictions.or(Restrictions.eq("email", usuario.getEmail()),
                        Restrictions.eq("cpf", usuario.getCpf())))
                .setProjection(Projections.rowCount())
                .uniqueResult();
        if (counted == 0L) {
            super.add(usuario);
        } else {
            throw new UniqueException(format("Não foi possível incluir o usuario %s pois já existe outro com o email %s ou com o CPF %s", usuario.getNomeCompleto(), usuario.getEmail(), usuario.getCpf()));
        }


    }

    public ResultList<Usuario> search(final Usuario usuario, final Integer page) {
        final Criteria criteria = this.getSession()
                .createCriteria(Usuario.class)
                .addOrder(Order.asc("id"));

        if (usuario != null) {
            if (!GenericValidator.isBlankOrNull(usuario.getEmail())) {
                criteria.add(Restrictions.eq("email", usuario.getEmail()));
            }
            if (!GenericValidator.isBlankOrNull(usuario.getNomeCompleto())) {
                criteria.add(Restrictions.like("nomeCompleto", usuario.getNomeCompleto(), MatchMode.END));
            }
        }

        return new ResultList<>(criteria, page);
    }
}
