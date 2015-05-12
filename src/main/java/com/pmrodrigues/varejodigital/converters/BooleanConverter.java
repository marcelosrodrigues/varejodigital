package com.pmrodrigues.varejodigital.converters;

import br.com.caelum.vraptor.Convert;
import br.com.caelum.vraptor.Converter;
import br.com.caelum.vraptor.ioc.RequestScoped;
import org.apache.commons.validator.GenericValidator;

import java.util.ResourceBundle;

/**
 * Created by Marceloo on 11/12/2014.
 */
@Convert(Boolean.class)
@RequestScoped
public class BooleanConverter implements Converter<Boolean> {

    @Override
    public Boolean convert(final String value, final Class<? extends Boolean> type, final ResourceBundle bundle) {
        if (GenericValidator.isBlankOrNull(value)) {
            return false;
        } else {
            return Boolean.parseBoolean(value);
        }
    }
}
