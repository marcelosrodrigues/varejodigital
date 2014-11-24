package com.pmrodrigues.ellasa.rest;

import br.com.caelum.vraptor.*;
import br.com.caelum.vraptor.view.Results;
import com.pmrodrigues.ellasa.models.MeioPagamento;
import com.pmrodrigues.ellasa.repositories.MeioPagamentoRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Marceloo on 24/11/2014.
 */
@Resource
public class MeioPagamentoController {

    private static final Logger logging = Logger.getLogger(MeioPagamentoController.class);

    private final MeioPagamentoRepository repository;

    private final Result result;

    public MeioPagamentoController(final MeioPagamentoRepository repository , final Result result ){
        this.repository = repository;
        this.result = result;
    }

    @Get
    @Path("/formaspagamento.json")
    @Consumes("application/json")
    public List<MeioPagamento> listar() {

        final List<MeioPagamento> meiospagamento = repository.list();

        result.use(Results.json())
               .from(meiospagamento)
               .serialize();

        return meiospagamento;
    }

}
