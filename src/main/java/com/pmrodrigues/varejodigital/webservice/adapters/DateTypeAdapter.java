package com.pmrodrigues.varejodigital.webservice.adapters;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.util.Date;
import java.util.Locale;

import static org.apache.commons.validator.GenericValidator.isDate;

/**
 * Created by Marceloo on 16/04/2015.
 */
public class DateTypeAdapter extends XmlAdapter<String, Date> {
    @Override
    public Date unmarshal(final String value) throws Exception {
        if (isDate(value, "dd-MM-yyyy", false)
                || isDate(value, "MM-yyyy", false)
                || isDate(value, "dd/MM/yyyy", false)
                || isDate(value, "yyyy-MM-dd", false)) {

            return DateUtils.parseDateStrictly(value, new String[]{
                    "dd-MM-yyyy",
                    "MM-yyyy",
                    "dd/MM/yyyy",
                    "yyyy-MM-dd"});
        }
        return null;
    }

    @Override
    public String marshal(final Date value) throws Exception {
        return DateFormatUtils.format(value, "yyyy-MM-dd"
                , new Locale("pt", "BR"));
    }
}
