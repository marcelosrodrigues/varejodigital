package com.pmrodrigues.ellasa.converters;

import br.com.caelum.vraptor.Convert;
import br.com.caelum.vraptor.ioc.RequestScoped;
import com.pmrodrigues.ellasa.models.Imagem;
import com.pmrodrigues.ellasa.repositories.ImagemRepository;

/**
 * Created by Marceloo on 04/03/2015.
 */
@Convert(Imagem.class)
@RequestScoped
public class ImagemConverter extends AbstractTypeConverter<Imagem> {
    public ImagemConverter(final ImagemRepository repository) {
        super(repository);
    }
}
