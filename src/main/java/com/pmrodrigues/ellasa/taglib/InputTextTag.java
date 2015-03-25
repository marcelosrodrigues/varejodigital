package com.pmrodrigues.ellasa.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;
import java.util.ResourceBundle;

import static java.lang.String.format;

/**
 * Created by Marceloo on 13/01/2015.
 */
public class InputTextTag extends AbstractTagLib {

    private final ResourceBundle message = ResourceBundle.getBundle("labels");

    private String label;
    private String value;
    private String id;

    public void setId(final String id) {
        this.id = id;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    @Override
    public void doTag() throws JspException, IOException {
        final JspWriter writer = getJspContext().getOut();

        final String errorCss = this.getError();

        final String label = message.getString(this.label);
        writer.print(format("<div class=\"form-group %s\">", errorCss));
        writer.print(format("<label for=\"%s\" class=\"control-label\">%s</label>", this.id, label));
        writer.print(format("<input type=\"text\" value=\"%s\" id=\"%s\" name=\"%s\" class=\"form-control\" placeholder=\"%s\" />",
                this.value == null ? "" : this.value, this.id, this.id, label));
        writer.print("</div>");

        writer.flush();
    }

}
