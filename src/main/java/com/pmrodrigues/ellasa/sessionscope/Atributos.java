package com.pmrodrigues.ellasa.sessionscope;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;
import com.pmrodrigues.ellasa.models.Atributo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by Marceloo on 09/03/2015.
 */
@Component
@SessionScoped
public class Atributos {

    private List<Atributo> atributos = new ArrayList<>();

    public void adicionar(final String valor) {
        final Atributo atributo = new Atributo(valor);
        if (!atributos.contains(atributo)) {
            atributos.add(atributo);
        }
    }

    public void remover(final String valor) {
        final Collection<Atributo> toRemove = CollectionUtils.select(this.atributos, new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                Atributo atributo = (Atributo) object;
                return (valor.equalsIgnoreCase(atributo.getDescricao()));
            }
        });

        atributos.removeAll(toRemove);
    }

    public List<Atributo> getAtributos() {
        return atributos;
    }

    public void apagar() {
        this.atributos.clear();
    }
}
