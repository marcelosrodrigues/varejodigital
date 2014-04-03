package com.pmrodrigues.ellasa.repositories;

import static java.lang.String.format;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.log4j.Logger;

public abstract class AbstractRepository<E> implements Repository<E> {

	private static final long serialVersionUID = 1L;

	@PersistenceContext
	private EntityManager entityManager;

	private final Class<E> persistentClass;

	private static final Logger logging = Logger
			.getLogger(AbstractRepository.class);
	
	@SuppressWarnings("unchecked")
	public AbstractRepository() {
		final ParameterizedType type = (ParameterizedType) this.getClass()
				.getGenericSuperclass();
		this.persistentClass = (Class<E>) type.getActualTypeArguments()[0];
	}

	@Override
	public void add(E e) {

		logging.debug(format(
				"Tentando inserir %s novo valor no banco de dados", e));
		this.entityManager.persist(e);
		logging.debug(format(" %s salvo com sucesso", e));
	}

	@Override
	public void set(E e) {
		logging.debug(format("Atualizando o valor %s no banco de dados", e));
		this.entityManager.merge(e);
		logging.debug(format("%s salvo com sucesso", e));

	}

	@Override
	public void remove(E e) {
		logging.debug(format("Removendo o valor %s do banco de dados", e));
		this.entityManager.remove(e);
		logging.debug(format("%s removido do banco de dados", e));
	}

	@Override
	public E findById(final Serializable id) {
		logging.debug(format(
				"Recuperando o valor de %s do banco de dados pela chave %s",
				persistentClass.getName(), id));
		E e = this.entityManager.find(persistentClass, id);
		logging.debug(format("Valor encontrado %s", e));
		return e;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<E> list() {
		logging.debug(format(
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
