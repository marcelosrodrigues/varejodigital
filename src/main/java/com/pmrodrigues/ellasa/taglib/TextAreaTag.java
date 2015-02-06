package com.pmrodrigues.ellasa.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;
import java.util.ResourceBundle;

import static java.lang.String.format;

/**
 * Created by Marceloo on 13/01/2015.
 */
public class TextAreaTag extends AbstractTagLib {

    private ResourceBundle message = ResourceBundle.getBundle("labels");

    private String label;
    private String value;
    private String id;
    private int rows;

    public void setId(String id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setLinhas(int linhas) {
        this.rows = linhas;
    }

    @Override
    public void doTag() throws JspException, IOException {
        final JspWriter writer = getJspContext().getOut();

        String errorCss = this.getError();

        final String label = message.getString(this.label);
        writer.print(format("<div class=\"form-group %s\">", errorCss));
        writer.print(format("<label for=\"%s\" class=\"control-label\">%s</label>", this.id, label));
        writer.print(format("<textarea id=\"%s\" name=\"%s\" class=\"form-control\" rows=\"%s\">%s</textarea>",
                this.id, this.id, this.rows, this.value == null ? "" : this.value));
        writer.print("</div>");

        writer.flush();
    }

}
