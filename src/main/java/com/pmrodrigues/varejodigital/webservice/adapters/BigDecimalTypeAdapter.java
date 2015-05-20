package com.pmrodrigues.varejodigital.webservice.adapters;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Created by Marceloo on 16/04/2015.
 */
public class BigDecimalTypeAdapter extends XmlAdapter<String, BigDecimal> {

    private static final DecimalFormat FORMAT =
            new DecimalFormat("0.00#", DecimalFormatSymbols.getInstance(new Locale("pt", "BR")));

    @Override
    public BigDecimal unmarshal(final String value) throws Exception {
        return new BigDecimal(FORMAT.parse(value).doubleValue()).round(MathContext.DECIMAL32);
    }

    @Override
    public String marshal(final BigDecimal value) throws Exception {
        return FORMAT.format(value);
    }
}
