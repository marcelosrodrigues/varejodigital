package com.pmrodrigues.ellasa.repositories.impl;

import com.pmrodrigues.ellasa.models.OrdemPagamento;
import com.pmrodrigues.ellasa.repositories.OrdemPagamentoRepository;
import org.springframework.stereotype.Repository;

@Repository("OrdemPagamentoRepository")
public class OrdemPagamentoRepositoryImpl
        extends
        AbstractRepository<OrdemPagamento> implements OrdemPagamentoRepository {

    private static final long serialVersionUID = 1L;

}
