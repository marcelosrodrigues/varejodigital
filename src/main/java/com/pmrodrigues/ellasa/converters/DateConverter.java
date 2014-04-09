package com.pmrodrigues.ellasa.converters;

import java.text.ParseException;
import java.util.Date;
import java.util.ResourceBundle;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.log4j.Logger;

import br.com.caelum.vraptor.Convert;
import br.com.caelum.vraptor.Converter;
import br.com.caelum.vraptor.ioc.RequestScoped;

@Convert(Date.class)
@RequestScoped
public class DateConverter implements Converter<Date> {

	private static final Logger LOGGER = Logger.getLogger(DateConverter.class);

	@Override
	public Date convert(final String value, final Class<? extends Date> type,
			final ResourceBundle bundle) {

		Date dataNascimento = null;
		try {

			if (GenericValidator.isDate(value, "dd-MM-yyyy", true)
					|| GenericValidator.isDate(value, "MM-yyyy", true)) {

				dataNascimento = DateUtils.parseDate(value, new String[]{
						"dd-MM-yyyy",
						"MM-yyyy"});
			}
		} catch (ParseException e) {
			LOGGER.warn("erro de conversão de data", e);
		}
		return dataNascimento;
	}

}
