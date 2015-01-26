package com.pmrodrigues.ellasa.rest;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import com.pmrodrigues.ellasa.models.Cliente;
import com.pmrodrigues.ellasa.repositories.ClienteRepository;
import org.apache.log4j.Logger;

import java.util.List;

@Resource
public class ClienteController {

    private final ClienteRepository repository;

    private final Result result;

    private final static Logger logging = Logger.getLogger(ClienteController.class);

    public ClienteController(final ClienteRepository repository, final Result result) {
        this.repository = repository;
        this.result = result;
    }

    @Get
    @Path("/clientes.json")
    public List<Cliente> clientes() {
        logging.debug("listando todos os clientes cadastrados no sistema");
        List<Cliente> clientes = repository.list();
        logging.debug("cliente encontrado com sucesso");

        result.use(Results.json())
                .from(clientes)
                .include("endereco", "endereco.estado")
                .exclude("endereco.estado.uf", "endereco.estado.nome", "dataCriacaco", "dataAlteracao", "endereco.dataCriacaco", "endereco.dataAlteracao")
                .serialize();


        return clientes;
    }

}
