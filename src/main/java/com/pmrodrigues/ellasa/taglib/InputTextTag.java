package com.pmrodrigues.ellasa.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.Map;
import java.util.ResourceBundle;

import static java.lang.String.format;

/**
 * Created by Marceloo on 13/01/2015.
 */
public class InputTextTag extends SimpleTagSupport {

    private ResourceBundle message = ResourceBundle.getBundle("labels");

    private String label;
    private String value;
    private String id;


    public void setId(String id) {
        this.id = id;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public void doTag() throws JspException, IOException {
        final JspWriter writer = getJspContext().getOut();
        final Map<String, String> errors = (Map<String, String>) getJspContext().getAttribute("errors");

        String errorCss = "";

        if (errors != null && !errors.isEmpty()) {
            if (errors.containsKey(this.id)) {
                errorCss = "has-error";
            }
        }


        final String label = message.getString(this.label);
        writer.print(format("<div class=\"form-group %s\">", errorCss));
        writer.print(format("<label for=\"%s\">%s</label>", this.id, label));
        writer.print(format("<input type=\"text\" value=\"%s\" id=\"%s\" name=\"%s\" class=\"form-control\" placeholder=\"%s\" />",
                this.value == null ? "" : this.value, this.id, this.id, label));
        writer.print("</div>");

        writer.flush();
    }
}
