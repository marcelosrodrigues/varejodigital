package com.pmrodrigues.ellasa.converters;

import br.com.caelum.vraptor.Convert;
import br.com.caelum.vraptor.Converter;
import br.com.caelum.vraptor.ioc.RequestScoped;
import org.apache.commons.validator.GenericValidator;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Marceloo on 06/02/2015.
 */
@Convert(BigDecimal.class)
@RequestScoped
public class BigDecimalConverter implements Converter<BigDecimal> {

    private static final DecimalFormat FORMAT = new DecimalFormat("#.##", DecimalFormatSymbols.getInstance(new Locale("pt", "BR")));

    @Override
    public BigDecimal convert(String value, Class<? extends BigDecimal> type, ResourceBundle bundle) {
        try {
            if (!GenericValidator.isBlankOrNull(value)) {
                Number formatted = FORMAT.parse(value);
                return new BigDecimal(formatted.doubleValue());
            }
            return null;
        } catch (ParseException e) {
            return null;
        }


    }
}
