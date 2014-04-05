package com.pmrodrigues.ellasa.repositories.impl;

import org.springframework.stereotype.Repository;

import com.pmrodrigues.ellasa.models.Estado;
import com.pmrodrigues.ellasa.repositories.EstadoRepository;

@Repository("EstadoRepository")
public class EstadoRepositoryImpl extends AbstractRepository<Estado> implements EstadoRepository {

	private static final long serialVersionUID = 1L;

}
