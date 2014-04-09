package com.pmrodrigues.ellasa.repositories.impl;

import static java.lang.String.format;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.pmrodrigues.ellasa.repositories.Repository;

@Transactional(propagation = Propagation.REQUIRED)
public abstract class AbstractRepository<E> implements Repository<E> {

	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager entityManager;

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

		LOGGER.debug(format(
				"Tentando inserir %s novo valor no banco de dados", e));
		this.entityManager.persist(e);
		this.entityManager.flush();
		LOGGER.debug(format(" %s salvo com sucesso", e));
	}

	@Override
	public void set(E e) {
		LOGGER.debug(format("Atualizando o valor %s no banco de dados", e));
		this.entityManager.merge(e);
		this.entityManager.flush();
		LOGGER.debug(format("%s salvo com sucesso", e));

	}

	@Override
	public void remove(E e) {
		LOGGER.debug(format("Removendo o valor %s do banco de dados", e));
		this.entityManager.remove(e);
		this.entityManager.flush();
		LOGGER.debug(format("%s removido do banco de dados", e));
	}

	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public E findById(final Serializable id) {
		LOGGER.debug(format(
				"Recuperando o valor de %s do banco de dados pela chave %s",
				persistentClass.getName(), id));
		E e = this.entityManager.find(persistentClass, id);
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

		final List<E> all = this.entityManager.createNamedQuery(
				format("%s.All", className))
				.getResultList();
		return all;
	}

	protected EntityManager getEntityManager() {
		return this.entityManager;
	}

}
