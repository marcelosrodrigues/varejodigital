package com.pmrodrigues.varejodigital.taglib;

import br.com.caelum.vraptor.validator.ValidationMessage;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.util.List;

/**
 * Created by Marceloo on 23/01/2015.
 */
abstract class AbstractTagLib extends SimpleTagSupport {

    private String errorField;

    public void setErrorField(final String errorField) {
        this.errorField = errorField;
    }

    public String getError() {
        final List<ValidationMessage> errors = (List<ValidationMessage>) getJspContext().getAttribute("errors", PageContext.REQUEST_SCOPE);

        if (errors != null && !errors.isEmpty()) {
            for (final ValidationMessage message : errors) {
                if (message.getCategory().equalsIgnoreCase(this.errorField)) {
                    return "has-error";
                }
            }
        }
        return "";
    }

}
