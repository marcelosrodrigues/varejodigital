package com.pmrodrigues.varejodigital.rest;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import com.pmrodrigues.varejodigital.models.MeioPagamento;
import com.pmrodrigues.varejodigital.repositories.MeioPagamentoRepository;

import java.util.List;

/**
 * Created by Marceloo on 24/11/2014.
 */
@Resource
public class MeioPagamentoController {

    private final MeioPagamentoRepository repository;

    private final Result result;

    public MeioPagamentoController(final MeioPagamentoRepository repository, final Result result) {
        this.repository = repository;
        this.result = result;
    }

    @Get
    @Path("/formaspagamento.json")
    public List<MeioPagamento> listar() {

        final List<MeioPagamento> meiospagamento = repository.list();

        result.use(Results.json())
                .from(meiospagamento)
                .serialize();

        return meiospagamento;
    }

}
