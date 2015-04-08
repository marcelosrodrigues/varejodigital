package com.pmrodrigues.ellasa.sessionscope;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;
import com.pmrodrigues.ellasa.models.Usuario;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Marceloo on 30/03/2015.
 */
@Component
@SessionScoped
public class Membros {

    private final Collection<Usuario> usuarios = new ArrayList<>();
    private final Collection<Usuario> removidos = new ArrayList<>();

    public void adicionar(final Usuario usuario) {

        if (removidos.contains(usuario)) {
            removidos.remove(usuario);
        }

        if (!usuarios.contains(usuario)) {
            usuarios.add(usuario);
        }
    }

    public void remover(final Usuario usuario) {
        if (usuarios.contains(usuario)) {
            usuarios.remove(usuario);
        }
        if (!removidos.contains(usuario)) {
            removidos.add(usuario);
        }
    }

    public Collection<Usuario> getNovosMembros() {
        return usuarios;
    }

    public Collection<Usuario> getRemovidos() {
        return removidos;
    }
}
