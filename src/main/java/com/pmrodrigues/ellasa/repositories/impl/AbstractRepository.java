package com.pmrodrigues.ellasa.repositories.impl;

import static java.lang.String.format;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.PrePersist;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pmrodrigues.ellasa.repositories.Repository;

@Transactional(propagation = Propagation.REQUIRED)
public abstract class AbstractRepository<E> implements Repository<E> {

	private static final long serialVersionUID = 1L;

	@Autowired
	private SessionFactory sessionFactory;

	private final Class<E> persistentClass;

	private static final Logger LOGGER = Logger
			.getLogger(AbstractRepository.class);

	@SuppressWarnings("unchecked")
	public AbstractRepository() {
		final ParameterizedType type = (ParameterizedType) this.getClass()
				.getGenericSuperclass();
		this.persistentClass = (Class<E>) type.getActualTypeArguments()[0];
	}

	@Override
	public void add(E e) {

		LOGGER.debug(format("Tentando inserir %s novo valor no banco de dados",
				e));

		preInsert(e);

		this.getSession().save(e);
		LOGGER.debug(format(" %s salvo com sucesso", e));
	}

	// TODO isto deve sair daqui e virar um EventListener.
	private void preInsert(E e) {
		for (Method method : e.getClass().getMethods()) {
			if (method.isAnnotationPresent(PrePersist.class)) {
				try {
					method.invoke(e);
				} catch (IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e1) {
					throw new RuntimeException(e1);
				}
			}
		}
	}

	@Override
	public void set(E e) {
		LOGGER.debug(format("Atualizando o valor %s no banco de dados", e));
		this.getSession().update(e);
		LOGGER.debug(format("%s salvo com sucesso", e));

	}

	@Override
	public void remove(E e) {
		LOGGER.debug(format("Removendo o valor %s do banco de dados", e));
		this.getSession().delete(e);
		LOGGER.debug(format("%s removido do banco de dados", e));
	}

	@SuppressWarnings("unchecked")
	@Transactional(propagation = Propagation.SUPPORTS)
	@Override
	public E findById(final Serializable id) {
		LOGGER.debug(format(
				"Recuperando o valor de %s do banco de dados pela chave %s",
				persistentClass.getName(), id));
		E e = (E) this.getSession().get(persistentClass, id);
		LOGGER.debug(format("Valor encontrado %s", e));
		return e;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public List<E> list() {
		LOGGER.debug(format(
				"Listando todos os valores de %s do banco de dados",
				persistentClass.getCanonicalName()));

		final String className = persistentClass.getCanonicalName().substring(
				(persistentClass.getPackage().getName() + ".").length());

		
		
		final List<E> all = this.getSession()
				.getNamedQuery(format("%s.All", className)).list();
		return all;
	}

	protected Session getSession() {
		
		Session session = this.sessionFactory.getCurrentSession();
		if (session == null || !session.isOpen()) {
			session = this.sessionFactory.openSession();
		}
		return session;
	}

}
