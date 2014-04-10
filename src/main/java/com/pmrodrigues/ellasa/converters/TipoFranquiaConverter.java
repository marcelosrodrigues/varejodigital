package com.pmrodrigues.ellasa.converters;

import br.com.caelum.vraptor.Convert;
import br.com.caelum.vraptor.ioc.RequestScoped;

import com.pmrodrigues.ellasa.models.TipoFranquia;
import com.pmrodrigues.ellasa.repositories.TipoFranquiaRepository;

@Convert(TipoFranquia.class)
@RequestScoped
public class TipoFranquiaConverter extends AbstractTypeConverter<TipoFranquia> {


	public TipoFranquiaConverter(final TipoFranquiaRepository repository) {
		super(repository);
	}
}
