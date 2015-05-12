package com.pmrodrigues.varejodigital.repositories.impl;

import com.pmrodrigues.varejodigital.repositories.Repository;
import com.pmrodrigues.varejodigital.repositories.ResultList;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import static java.lang.String.format;

@Transactional(propagation = Propagation.REQUIRED)
public abstract class AbstractRepository<E> implements Repository<E> {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = Logger
            .getLogger(AbstractRepository.class);
    private final Class<E> persistentClass; //NOPMD
    @Autowired
    private SessionFactory sessionFactory; //NOPMD

    @SuppressWarnings("unchecked")
    public AbstractRepository() {
        final ParameterizedType type = (ParameterizedType) this.getClass()
                .getGenericSuperclass();
        this.persistentClass = (Class<E>) type.getActualTypeArguments()[0];
    }

    @Override
    public void add(final E e) {

        LOGGER.debug(format("Tentando inserir %s novo valor no banco de dados",
                e));
        this.getSession().save(e);
        LOGGER.debug(format(" %s salvo com sucesso", e));

    }

    @Override
    public void set(final E entity) {
        LOGGER.debug(format("Atualizando o valor %s no banco de dados", entity));
        this.getSession().update(entity);
        this.getSession().flush();
        LOGGER.debug(format("%s salvo com sucesso", entity));

    }

    @Override
    public void remove(final E entity) {
        LOGGER.debug(format("Removendo o valor %s do banco de dados", entity));
        this.getSession().delete(entity);
        LOGGER.debug(format("%s removido do banco de dados", entity));
    }

    @SuppressWarnings("unchecked")
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public E findById(final Serializable id) { //NOPMD
        LOGGER.debug(format(
                "Recuperando o valor de %s do banco de dados pela chave %s",
                persistentClass.getName(), id));
        final E entity = (E) this.getSession().get(persistentClass, id);
        LOGGER.debug(format("Valor encontrado %s", entity));
        return entity;
    }

    @SuppressWarnings("unchecked")
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<E> list() {
        LOGGER.debug(format(
                "Listando todos os valores de %s do banco de dados",
                persistentClass.getCanonicalName()));

        final String className = persistentClass.getSimpleName();


        final List<E> all = this.getSession()
                .getNamedQuery(format("%s.All", className))
                .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY)
                .list();
        return all;
    }

    protected Session getSession() {
        return this.sessionFactory.getCurrentSession();
    }

    public ResultList<E> search(final E e, final Integer page) {
        return null;
    }

    public ResultList<E> search(final E e) {
        return search(e, 0);
    }

    @Override
    public void enableFilter(final String filtro, final Serializable e) {
        this.getSession().enableFilter(filtro)
                .setParameter(filtro, e);
    }
}
