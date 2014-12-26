package com.pmrodrigues.ellasa.converters;

import br.com.caelum.vraptor.Convert;
import br.com.caelum.vraptor.ioc.RequestScoped;
import com.pmrodrigues.ellasa.models.Perfil;
import com.pmrodrigues.ellasa.repositories.PerfilRepository;

/**
 * Created by Marceloo on 18/12/2014.
 */
@Convert(Perfil.class)
@RequestScoped
public class PerfilConverter extends AbstractTypeConverter<Perfil> {
    public PerfilConverter(PerfilRepository repository) {
        super(repository);
    }
}
