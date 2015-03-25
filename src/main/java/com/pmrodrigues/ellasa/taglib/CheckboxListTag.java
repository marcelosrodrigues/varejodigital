package com.pmrodrigues.ellasa.taglib;

import org.apache.commons.beanutils.PropertyUtils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import static java.lang.String.format;

/**
 * Created by Marceloo on 19/01/2015.
 */
public class CheckboxListTag extends AbstractTagLib {

    private String id;
    private String label;
    private Collection value;
    private String labelField;
    private String valueField;
    private Collection checked;


    public void setLabel(final String label) {
        this.label = label;
    }

    public void setValue(final Collection value) {
        this.value = value;
    }

    public void setLabelField(final String labelField) {
        this.labelField = labelField;
    }

    public void setValueField(final String valueField) {
        this.valueField = valueField;
    }

    public void setChecked(final Collection checked) {
        this.checked = checked;
    }

    public void setId(final String id) {
        this.id = id;
    }

    @Override
    public void doTag() throws JspException, IOException {

        try {
            final JspWriter out = this.getJspContext().getOut();
            final String errorCss = this.getError();

            out.write(format("<div class=\"form-group %s\">", errorCss));
            out.write(format("<label for=\"%s\">%s </label>&nbsp;", this.id, this.label));

            for (final Object item : value) {
                out.write("<label class=\"checkbox-inline\">");

                final Object value = PropertyUtils.getProperty(item, valueField);
                final Object label = PropertyUtils.getProperty(item, labelField);

                out.write(format("<input type=\"checkbox\" name=\"%s\" id=\"%s\" value=\"%s\" %s>%s",
                        this.id, this.id, value, this.checked != null && this.checked.contains(item) ? "checked=\"checked\"" : "", label));
                out.write("</label>");
            }
            out.write("</div>");
            out.flush();
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new JspException(e);

        }

    }

}
