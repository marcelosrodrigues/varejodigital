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

	private static final Logger logging = Logger.getLogger(DateConverter.class);

	@Override
	public Date convert(String value, Class<? extends Date> type,
			ResourceBundle bundle) {

		try {

			if (GenericValidator.isDate(value, "dd-MM-yyyy", true)
					|| GenericValidator.isDate(value, "MM-yyyy", true)) {

				return DateUtils.parseDate(value, new String[]{"dd-MM-yyyy",
						"MM-yyyy"});
			} else {
				return null;
			}
		} catch (ParseException e) {
			logging.warn("erro de conversão de data", e);
			return null;
		}
	}

}
