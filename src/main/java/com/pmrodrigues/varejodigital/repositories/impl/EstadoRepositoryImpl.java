package com.pmrodrigues.varejodigital.repositories.impl;

import com.pmrodrigues.varejodigital.models.Estado;
import com.pmrodrigues.varejodigital.repositories.EstadoRepository;
import org.springframework.stereotype.Repository;

@Repository("EstadoRepository")
public class EstadoRepositoryImpl extends AbstractRepository<Estado> implements EstadoRepository {

    private static final long serialVersionUID = 1L;

}
