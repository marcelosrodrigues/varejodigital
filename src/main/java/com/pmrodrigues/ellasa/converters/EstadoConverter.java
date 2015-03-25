package com.pmrodrigues.ellasa.converters;

import br.com.caelum.vraptor.Convert;
import br.com.caelum.vraptor.ioc.RequestScoped;
import com.pmrodrigues.ellasa.models.Estado;
import com.pmrodrigues.ellasa.repositories.EstadoRepository;

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
