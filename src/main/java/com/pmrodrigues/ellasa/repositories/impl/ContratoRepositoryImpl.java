package com.pmrodrigues.ellasa.repositories.impl;

import com.pmrodrigues.ellasa.models.Contrato;
import com.pmrodrigues.ellasa.repositories.ContratoRepository;
import org.springframework.stereotype.Repository;

@Repository("ContratoRepository")
public class ContratoRepositoryImpl extends AbstractRepository<Contrato>
        implements
        ContratoRepository {

    private static final long serialVersionUID = 1L;

}
