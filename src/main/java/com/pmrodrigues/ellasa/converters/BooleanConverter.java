package com.pmrodrigues.ellasa.converters;

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
    public Boolean convert(String value, Class<? extends Boolean> type, ResourceBundle bundle) {
        if(GenericValidator.isBlankOrNull(value)){
            return false;
        } else {
            return Boolean.parseBoolean(value);
        }
    }
}
