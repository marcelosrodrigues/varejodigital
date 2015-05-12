package com.pmrodrigues.varejodigital.rest;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;
import com.pmrodrigues.varejodigital.models.Loja;
import com.pmrodrigues.varejodigital.models.Produto;
import com.pmrodrigues.varejodigital.repositories.ProdutoRepository;
import org.apache.log4j.Logger;

import java.util.List;

@Resource
public class ProdutoController {

    private static final Logger logging = Logger.getLogger(ProdutoController.class);
    private final ProdutoRepository repository;
    private final Result result;

    public ProdutoController(final ProdutoRepository repository, final Result result) {
        this.repository = repository;
        this.result = result;
    }

    @Get
    @Path("/{loja}/produtos.json")
    public List<Produto> produtos(final Loja loja) {

        logging.debug("iniciando a leitura dos produtos cadastrados no prestashop");
        final List<Produto> produtos = repository.listByLoja(loja);
        logging.debug("lista encontrada");

        result.use(Results.json())
                .from(produtos)
                .include("imagens", "atributos", "secao")
                .exclude("secao.nome", "secao.pai", "secao.subsecoes")
                .serialize();

        return produtos;
    }
}
