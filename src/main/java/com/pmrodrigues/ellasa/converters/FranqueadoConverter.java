package com.pmrodrigues.ellasa.converters;

import java.util.ResourceBundle;

import org.apache.commons.validator.GenericValidator;

import br.com.caelum.vraptor.Convert;
import br.com.caelum.vraptor.ioc.RequestScoped;

import com.pmrodrigues.ellasa.models.Franqueado;
import com.pmrodrigues.ellasa.repositories.FranqueadoRepository;

@Convert(Franqueado.class)
@RequestScoped
public class FranqueadoConverter extends AbstractTypeConverter<Franqueado> {

	public FranqueadoConverter(FranqueadoRepository repository) {
		super(repository);
	}

	@Override
	public Franqueado convert(final String value,
			final Class<? extends Franqueado> type, final ResourceBundle bundle) {

		Franqueado franqueado = null;
		if (GenericValidator.isLong(value)) {
			franqueado = super.convert(value, type, bundle);
		} else {
			
			if( !GenericValidator.isEmail(value) ){
				franqueado = ((FranqueadoRepository) super.getRepository())
						.findByCodigo(value);
			}
			
		}

		return franqueado;
	}

}
