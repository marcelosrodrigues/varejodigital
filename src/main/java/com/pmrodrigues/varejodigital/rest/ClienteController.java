package com.pmrodrigues.varejodigital.rest;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import com.pmrodrigues.varejodigital.models.Cliente;
import com.pmrodrigues.varejodigital.repositories.ClienteRepository;
import org.apache.log4j.Logger;

import java.util.List;

@Resource
public class ClienteController {

    private final static Logger logging = Logger.getLogger(ClienteController.class);
    private final ClienteRepository repository;
    private final Result result;

    public ClienteController(final ClienteRepository repository, final Result result) {
        this.repository = repository;
        this.result = result;
    }

    @Get
    @Path("/clientes.json")
    public List<Cliente> clientes() {
        logging.debug("listando todos os clientes cadastrados no sistema");
        final List<Cliente> clientes = repository.list();
        logging.debug("cliente encontrado com sucesso");

        result.use(Results.json())
                .from(clientes)
                .include("endereco", "endereco.estado")
                .exclude("endereco.estado.uf", "endereco.estado.nome", "dataCriacao", "dataAlteracao", "endereco.dataCriacao", "endereco.dataAlteracao")
                .serialize();


        return clientes;
    }

}
