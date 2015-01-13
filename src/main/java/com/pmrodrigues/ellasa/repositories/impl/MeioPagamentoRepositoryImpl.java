package com.pmrodrigues.ellasa.repositories.impl;

import com.pmrodrigues.ellasa.models.MeioPagamento;
import com.pmrodrigues.ellasa.repositories.MeioPagamentoRepository;
import org.springframework.stereotype.Repository;

@Repository("MeioPagamentoRepostory")
public class MeioPagamentoRepositoryImpl extends AbstractRepository<MeioPagamento> implements MeioPagamentoRepository {

    private static final long serialVersionUID = 1L;

}
