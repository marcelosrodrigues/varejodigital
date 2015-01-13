package com.pmrodrigues.ellasa.converters;

import br.com.caelum.vraptor.Convert;
import br.com.caelum.vraptor.ioc.RequestScoped;
import com.pmrodrigues.ellasa.models.Loja;
import com.pmrodrigues.ellasa.repositories.ShoppingRepository;

/**
 * Created by Marceloo on 13/01/2015.
 */
@Convert(Loja.class)
@RequestScoped
public class LojaConverter extends AbstractTypeConverter<Loja> {

    public LojaConverter(final ShoppingRepository repository) {
        super(repository);
    }
}
