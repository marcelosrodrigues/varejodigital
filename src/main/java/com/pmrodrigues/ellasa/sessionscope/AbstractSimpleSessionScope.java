package com.pmrodrigues.ellasa.sessionscope;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Marceloo on 13/04/2015.
 */
public abstract class AbstractSimpleSessionScope<T> {

    private final Collection<T> novos = new ArrayList<>();
    private final Collection<T> removidos = new ArrayList<>();

    public void adicionar(final T entity) {

        if (removidos.contains(entity)) {
            removidos.remove(entity);
        }

        if (!novos.contains(entity)) {
            novos.add(entity);
        }
    }

    public void remover(final T entity) {
        if (novos.contains(entity)) {
            novos.remove(entity);
        }
        if (!removidos.contains(entity)) {
            removidos.add(entity);
        }
    }

    public Collection<T> novos() {
        return novos;
    }

    public Collection<T> removidos() {
        return removidos;
    }

    public void apagar() {
        this.novos.clear();
        this.removidos.clear();
    }
}
