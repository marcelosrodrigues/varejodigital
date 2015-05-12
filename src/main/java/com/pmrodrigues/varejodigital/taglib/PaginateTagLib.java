package com.pmrodrigues.varejodigital.taglib;

import com.pmrodrigues.varejodigital.repositories.ResultList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.util.Enumeration;

import static java.lang.String.format;

/**
 * Created by Marceloo on 02/03/2015.
 */
public class PaginateTagLib extends SimpleTagSupport {

    private ResultList resultList;
    private String URL;

    @Override
    public void doTag() throws JspException, IOException {

        final JspWriter out = this.getJspContext().getOut();

        out.print("<div class=\"row\">");
        out.print("<div class=\"col-sm-5\">");
        out.print(format("<div class=\"dataTables_info\" id=\"dataTables-example_info\" role=\"alert\" " +
                        "aria-live=\"polite\" aria-relevant=\"all\">Página %s de %s</div></div>",
                resultList.getPage() + 1, resultList.getPageCount()));

        out.print("<div class=\"col-sm-7\" style=\"top: -30px;text-align:right;\"><div class=\"dataTables_paginate paging_simple_numbers\">");
        out.print("<ul class=\"pagination\">");

        out.print(this.adicionarBotaoPrevious());

        out.print(adicionaLinhaCentralPaginacao());

        out.print(this.adicionarBotaoNext());

        out.print("</ul></div></div></div>");


    }

    public String adicionarBotaoNext() {
        final StringBuffer out = new StringBuffer();
        out.append(format("<li class=\"paginate_button next %s\" aria-controls=\"dataTables-example\" tabindex=\"0\" id=\"dataTables-example_next\">", resultList.isNext() ? "" : "disabled"));
        out.append(format("<a href=\"/varejodigital%s?page=%s%s\">&gt;&gt;</a></li>", URL, resultList.isNext() ? this.resultList.getPage() + 1 : resultList.getPageCount(), recuperaParametrosConsulta()));

        return out.toString();
    }

    public String adicionarBotaoPrevious() {

        final StringBuffer out = new StringBuffer();
        out.append(format("<li class=\"paginate_button previous %s\" aria-controls=\"dataTables-example\" tabindex=\"0\" id=\"dataTables-example_previous\">",
                resultList.isPrevious() ? "" : "disabled"));
        out.append(format("<a href=\"/varejodigital%s?page=%s%s\">&lt;&lt;</a></li>", URL, resultList.isPrevious() ? resultList.getPage() - 1 : 0, recuperaParametrosConsulta()));
        return out.toString();

    }

    public String adicionaLinhaCentralPaginacao() {

        final StringBuffer out = new StringBuffer();

        long pagecount = resultList.getPageCount();
        long index = 0L;

        if (resultList.getPageCount() > 10L) {
            pagecount = resultList.getPage() + 10L;
            index = resultList.getPage();
        }

        for (; index < pagecount; index++) {
            out.append(format("<li class=\"paginate_button\" tabindex=\"%s\"><a href=\"/varejodigital%s?page=%s%s\">%s</a></li>", index, URL, index, recuperaParametrosConsulta(), index + 1));
        }

        return out.toString();
    }

    public void setResultList(final ResultList resultList) {
        this.resultList = resultList;
    }

    public void setUrl(final String URL) {
        this.URL = URL;
    }

    public String recuperaParametrosConsulta() {
        final StringBuffer parameters = new StringBuffer();
        final PageContext context = (PageContext) this.getJspContext();
        final HttpServletRequest request = (HttpServletRequest) context.getRequest();


        for (final Enumeration<String> names = request.getParameterNames(); names.hasMoreElements(); ) {
            final String name = names.nextElement();
            if (!"page".equalsIgnoreCase(name)) {
                parameters.append(format("&%s=%s", name, request.getParameter(name)));
            }
        }

        return parameters.toString();
    }

}


