package com.pmrodrigues.varejodigital.controllers;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import com.pmrodrigues.varejodigital.Constante;
import com.pmrodrigues.varejodigital.annotations.Before;
import com.pmrodrigues.varejodigital.annotations.CRUD;
import com.pmrodrigues.varejodigital.annotations.Insert;
import com.pmrodrigues.varejodigital.annotations.Update;
import com.pmrodrigues.varejodigital.controllers.crud.AbstractCRUDController;
import com.pmrodrigues.varejodigital.models.Atributo;
import com.pmrodrigues.varejodigital.models.Imagem;
import com.pmrodrigues.varejodigital.models.Produto;
import com.pmrodrigues.varejodigital.repositories.ProdutoRepository;
import com.pmrodrigues.varejodigital.repositories.SecaoRepository;
import com.pmrodrigues.varejodigital.repositories.ShoppingRepository;
import com.pmrodrigues.varejodigital.sessionscope.Atributos;
import com.pmrodrigues.varejodigital.sessionscope.Imagens;
import org.apache.log4j.Logger;

import static java.lang.String.format;


/**
 * Created by Marceloo on 05/02/2015.
 */
@Resource
@CRUD
public class ProdutoController extends AbstractCRUDController<Produto> {

    private static final Logger logging = Logger.getLogger(ProdutoController.class);
    private final Imagens imagens;
    private final ShoppingRepository shoppingRepository;
    private final SecaoRepository secaoRepository;
    private final Atributos atributos;

    public ProdutoController(final ProdutoRepository repository, final ShoppingRepository shoppingRepository,
                             final SecaoRepository secaoRepository, final Result result, final Validator validator,
                             final Imagens imagens, final Atributos atributos) {
        super(repository, result, validator);
        this.imagens = imagens;
        this.shoppingRepository = shoppingRepository;
        this.secaoRepository = secaoRepository;
        this.atributos = atributos;
    }

    @Before
    public void before() {
        this.getResult().include(Constante.DEPARTAMENTOS, secaoRepository.list());
        this.getResult().include(Constante.LOJAS, shoppingRepository.list());
    }

    @Insert
    public void insert(final Produto produto) {
        logging.debug(format("Iniciando o salvamento do produto %s", produto));

        adicionarLista(produto);

        this.getRepository().add(produto);
        this.imagens.apagar();
        logging.debug(format("Produto %s salvo com sucesso", produto));
    }

    public void adicionarLista(final Produto produto) {
        for (final Imagem imagem : this.imagens.getImagens()) {
            produto.adicionar(imagem);
        }

        for (final Atributo atributo : this.atributos.getAtributos()) {
            produto.adicionar(atributo);
        }
    }

    @Update
    public void update(final Produto produto) {
        logging.debug(format("Iniciando o salvamento do produto %s", produto));

        adicionarLista(produto);

        this.getRepository().set(produto);
        this.imagens.apagar();
        logging.debug(format("Produto %s salvo com sucesso", produto));
    }
}
