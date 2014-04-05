package com.pmrodrigues.ellasa.repositories.impl;

import org.springframework.stereotype.Repository;

import com.pmrodrigues.ellasa.models.TipoFranquia;
import com.pmrodrigues.ellasa.repositories.TipoFranquiaRepository;

@Repository("TipoFranquiaRepository")
public class TipoFranquiaRepositoryImpl extends AbstractRepository<TipoFranquia> implements TipoFranquiaRepository {

	private static final long serialVersionUID = 1L;

}
