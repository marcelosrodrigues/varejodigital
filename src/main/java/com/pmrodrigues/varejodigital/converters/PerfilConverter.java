package com.pmrodrigues.varejodigital.converters;

import br.com.caelum.vraptor.Convert;
import br.com.caelum.vraptor.ioc.RequestScoped;
import com.pmrodrigues.varejodigital.models.Perfil;
import com.pmrodrigues.varejodigital.repositories.PerfilRepository;

/**
 * Created by Marceloo on 18/12/2014.
 */
@Convert(Perfil.class)
@RequestScoped
public class PerfilConverter extends AbstractTypeConverter<Perfil> {
    public PerfilConverter(final PerfilRepository repository) {
        super(repository);
    }
}
