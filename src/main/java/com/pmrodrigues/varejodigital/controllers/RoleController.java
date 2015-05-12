package com.pmrodrigues.varejodigital.controllers;

import br.com.caelum.vraptor.*;
import br.com.caelum.vraptor.view.Results;
import com.pmrodrigues.varejodigital.annotations.CRUD;
import com.pmrodrigues.varejodigital.annotations.Update;
import com.pmrodrigues.varejodigital.controllers.crud.AbstractCRUDController;
import com.pmrodrigues.varejodigital.models.Perfil;
import com.pmrodrigues.varejodigital.models.Usuario;
import com.pmrodrigues.varejodigital.repositories.PerfilRepository;
import com.pmrodrigues.varejodigital.sessionscope.Membros;

import java.util.Collection;

/**
 * Created by Marceloo on 28/03/2015.
 */
@Resource
@CRUD
public class RoleController extends AbstractCRUDController<Perfil> {
    private final Membros membros;

    public RoleController(final PerfilRepository repository, final Membros membros, final Result result, final Validator validator) {
        super(repository, result, validator);
        this.membros = membros;
    }

    @Post
    @Path("/grupo/{usuario}/adicionar.json")
    public Collection<Usuario> adicionar(final Usuario usuario) {
        membros.adicionar(usuario);
        this.getResult().use(Results.json())
                .from(usuario)
                .include("id", "nomeCompleto", "email")
                .exclude("password", "cleanPassword", "bloqueado", "celular", "residencial",
                        "cpf", "dataNascimento", "perfis", "tentativas")
                .serialize();

        return membros.novos();
    }

    @Post
    @Path("/grupo/{usuario}/remover.json")
    public Collection<Usuario> remover(final Usuario usuario) {
        membros.remover(usuario);
        this.getResult().use(Results.json())
                .from(usuario)
                .include("id", "nomeCompleto", "email")
                .exclude("password", "cleanPassword", "bloqueado", "celular", "residencial",
                        "cpf", "dataNascimento", "perfis", "tentativas")
                .serialize();

        return membros.novos();
    }

    @Update
    public void update(final Perfil object) {

        final Perfil existed = getRepository().findById(object.getId());
        existed.remover(membros.removidos());
        existed.adicionar(membros.novos());

        getRepository().set(existed);

    }
}
