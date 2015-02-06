package com.pmrodrigues.ellasa.taglib;

import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.validator.GenericValidator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

import static java.lang.String.format;
import static org.apache.commons.beanutils.BeanUtils.getProperty;

/**
 * Created by Marceloo on 27/01/2015.
 */
public class RadioTreeViewTag extends SimpleTagSupport {

    private Collection list;

    private Object checked;

    private String value;

    private String label;

    private String subList;

    private String id;

    private String title;

    private String father;

    public void doTag() throws JspException, IOException {

        final JspWriter writer = getJspContext().getOut();
        writer.write(format("<div class=\"form-group checkbox-treeview\"><label>%s</label><ul>", title));

        try {
            createTreeView(list);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new JspException(e);
        }

        writer.write("</ul></div>");
        writer.flush();

    }

    private Object getNestedValue(Object entity, String property) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        try {
            return getProperty(entity, property);
        } catch (NestedNullException e) {
            return "0";
        }
    }

    private void createTreeView(final Collection list) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException {

        final JspWriter writer = getJspContext().getOut();
        for (final Object entity : list) {

            Collection sublist = null;
            if (!GenericValidator.isBlankOrNull(this.subList)) {
                sublist = (Collection) PropertyUtils.getProperty(entity, this.subList);
            }

            writer.write("<li>");

            if (sublist != null && !sublist.isEmpty()) {

                writer.write(format("<div class=\"form-group\"><label>%s</label></div>",
                        getProperty(entity, label)));

                writer.write("<ul>");
                createTreeView(sublist);
                writer.append("</ul>");

            } else {

                writer.write(format("<div class=\"radio\"><label><input type=\"radio\" id=\"%s\" name=\"%s\" father=\"%s\" value=\"%s\" %s />%s</label></div>",
                        format("%s", id),
                        format("%s", id),
                        getNestedValue(entity, father),
                        getProperty(entity, value),
                        entity.equals(checked) ? "checked=\"checked\"" : "",
                        getProperty(entity, label)));

            }

            writer.write("</li>");

        }
    }

    public void setList(final Collection list) {
        this.list = list;
    }

    public void setValue(final String value) {
        this.value = value;
    }

    public void setLabel(final String label) {
        this.label = label;
    }

    public void setSubList(final String subList) {
        this.subList = subList;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setChecked(Object checked) {
        this.checked = checked;
    }

    public void setFather(String father) {
        this.father = father;
    }
}
