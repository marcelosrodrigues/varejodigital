package com.pmrodrigues.ellasa.repositories.impl;

import com.pmrodrigues.ellasa.models.Estado;
import com.pmrodrigues.ellasa.repositories.EstadoRepository;
import org.springframework.stereotype.Repository;

@Repository("EstadoRepository")
public class EstadoRepositoryImpl extends AbstractRepository<Estado> implements EstadoRepository {

    private static final long serialVersionUID = 1L;

}
