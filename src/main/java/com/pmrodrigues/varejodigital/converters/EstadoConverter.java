package com.pmrodrigues.varejodigital.converters;

import br.com.caelum.vraptor.Convert;
import br.com.caelum.vraptor.ioc.RequestScoped;
import com.pmrodrigues.varejodigital.models.Estado;
import com.pmrodrigues.varejodigital.repositories.EstadoRepository;

/**
 * Created by Marceloo on 18/12/2014.
 */
@Convert(Estado.class)
@RequestScoped
public class EstadoConverter extends AbstractTypeConverter<Estado> {

    public EstadoConverter(final EstadoRepository repository) {
        super(repository);
    }
}
