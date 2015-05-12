package com.pmrodrigues.varejodigital.converters;

import br.com.caelum.vraptor.Convert;
import br.com.caelum.vraptor.ioc.RequestScoped;
import com.pmrodrigues.varejodigital.models.Usuario;
import com.pmrodrigues.varejodigital.repositories.UsuarioRepository;

/**
 * Created by Marceloo on 31/03/2015.
 */
@Convert(Usuario.class)
@RequestScoped
public class UsuarioConverter extends AbstractTypeConverter<Usuario> {
    public UsuarioConverter(final UsuarioRepository repository) {
        super(repository);
    }
}
