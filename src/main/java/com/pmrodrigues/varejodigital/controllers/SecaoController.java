package com.pmrodrigues.varejodigital.controllers;

import br.com.caelum.vraptor.*;
import br.com.caelum.vraptor.ioc.Component;
import com.pmrodrigues.varejodigital.Constante;
import com.pmrodrigues.varejodigital.annotations.*;
import com.pmrodrigues.varejodigital.controllers.crud.AbstractCRUDController;
import com.pmrodrigues.varejodigital.models.Imagem;
import com.pmrodrigues.varejodigital.models.Secao;
import com.pmrodrigues.varejodigital.repositories.SecaoRepository;
import com.pmrodrigues.varejodigital.sessionscope.Imagens;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.validator.GenericValidator;

/**
 * Created by Marceloo on 09/01/2015.
 */
@Component
@CRUD
public class SecaoController extends AbstractCRUDController<Secao> {

    private final Imagens imagens;

    public SecaoController(final SecaoRepository repository, final Result result, final Validator validator, final Imagens imagens) {
        super(repository, result, validator);
        this.imagens = imagens;
    }

    @Before
    public void before() {
        final SecaoRepository repository = (SecaoRepository) this.getRepository();
        this.getResult().include(Constante.DEPARTAMENTOS, repository.listAll());
    }


    @Get
    @Path("/secao/{secao}/novo.do")
    @Tiles(definition = "formulario-template")
    public void adicionarSubCategoria(final Secao secao) {
        this.before();
        final Secao object = new Secao();
        object.setPai(secao);
        this.getResult().include(Constante.OBJECT, object);
    }

    @Insert
    public void insert(final Secao object) {

        object.setIcone(getIcone());

        getRepository().add(object);
        imagens.apagar();

    }

    public String getIcone() {
        String icone = null;
        if (!imagens.getImagens().isEmpty()) {
            icone = ((Imagem) CollectionUtils.get(imagens.getImagens(), 0)).getUrl();
        }

        return icone;
    }

    @Update
    public void update(final Secao object) {
        final String icone = this.getIcone();
        final Secao existed = getRepository().findById(object.getId());
        if (!GenericValidator.isBlankOrNull(icone)) {
            existed.setIcone(icone);
        }


        existed.setNome(object.getNome());
        existed.setPai(object.getPai());

        getRepository().set(existed);
        imagens.apagar();
    }

    @Post
    @Path("/secao/{secao}/remover/icone.json")
    public void removerIcone(final Secao secao) {

        secao.setIcone(null);
        getRepository().set(secao);

    }
}
