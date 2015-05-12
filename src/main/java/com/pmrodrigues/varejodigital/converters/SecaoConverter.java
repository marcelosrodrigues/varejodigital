package com.pmrodrigues.varejodigital.converters;

import br.com.caelum.vraptor.Convert;
import br.com.caelum.vraptor.ioc.RequestScoped;
import com.pmrodrigues.varejodigital.models.Secao;
import com.pmrodrigues.varejodigital.repositories.SecaoRepository;

/**
 * Created by Marceloo on 12/01/2015.
 */
@Convert(Secao.class)
@RequestScoped
public class SecaoConverter extends AbstractTypeConverter<Secao> {

    public SecaoConverter(final SecaoRepository repository) {
        super(repository);
    }
}
