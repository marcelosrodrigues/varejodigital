package com.pmrodrigues.ellasa.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.ResourceBundle;

import static java.lang.String.format;
import static org.apache.commons.beanutils.PropertyUtils.getProperty;

/**
 * Created by Marceloo on 19/01/2015.
 */
public class SelectTag extends AbstractTagLib {

    private ResourceBundle message = ResourceBundle.getBundle("labels");

    private String label;

    private Collection list;
    private String id;
    private String valueField;
    private String labelField;
    private Object value;

    @Override
    public void doTag() throws JspException, IOException {

        try {
            final String label = message.getString(this.label);
            JspWriter out = getJspContext().getOut();

            String errorCss = this.getError();

            out.write(format("<div class=\"form-group %s\">", errorCss));
            out.write(format("<label class=\"control-label\" for=\"%s\">%s</label>", this.id, label));
            out.write(format("<select name=\"%s\" id=\"%s\" class=\"form-control\">", this.id, this.id));
            out.write("<option></option>");

            for (Object item : this.list) {
                out.write(format("<option value=\"%s\" %s>%s</option>",
                        getProperty(item, valueField),
                        (item.equals(value) ? "selected" : ""),
                        getProperty(item, labelField)));
            }

            out.write("</select>");
            out.write("</div>");
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new JspException(e);
        }
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setList(Collection list) {
        this.list = list;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setValueField(String valueField) {
        this.valueField = valueField;
    }

    public void setLabelField(String labelField) {
        this.labelField = labelField;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}
