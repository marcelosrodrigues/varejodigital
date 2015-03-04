package test.com.pmrodrigues.ellasa.taglib;

import com.pmrodrigues.ellasa.repositories.ResultList;
import com.pmrodrigues.ellasa.taglib.PaginateTagLib;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.springframework.mock.web.MockJspWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import java.io.StringWriter;

import static org.junit.Assert.assertEquals;

public class TestPaginateTagLib {

    private final Mockery context = new Mockery() {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };

    private PageContext jspContext = context.mock(PageContext.class);
    private StringWriter writer = new StringWriter();
    private ResultList resultlist = context.mock(ResultList.class);
    private HttpServletRequest request = context.mock(HttpServletRequest.class);

    @Test
    public void doTag() throws Exception {

        context.checking(new Expectations() {{
            oneOf(jspContext).getOut();
            will(returnValue(new MockJspWriter(writer)));

            allowing(jspContext).getRequest();
            will(returnValue(request));

            allowing(request);

            allowing(resultlist).getPageCount();
            will(returnValue(3L));

            allowing(resultlist).getPage();
            will(returnValue(0));

            allowing(resultlist).isPrevious();
            will(returnValue(Boolean.FALSE));

            allowing(resultlist).isNext();
            will(returnValue(Boolean.TRUE));
        }});


        PaginateTagLib paginate = new PaginateTagLib();
        paginate.setJspContext(jspContext);
        paginate.setResultList(resultlist);
        paginate.setUrl("/loja/pesquisar.do");

        paginate.doTag();

        String result = writer.toString();

        final String EXPECTED = "<div class=\"row\">" +
                "<div class=\"col-sm-6\">" +
                "<div class=\"dataTables_info\" id=\"dataTables-example_info\" role=\"alert\" aria-live=\"polite\" aria-relevant=\"all\">Página 1 de 3</div>" +
                "</div>" +
                "<div class=\"col-sm-6\" style=\"top: -30px;text-align:right;\"><div class=\"dataTables_paginate paging_simple_numbers\">" +
                "<ul class=\"pagination\">" +
                "<li class=\"paginate_button previous disabled\" aria-controls=\"dataTables-example\" tabindex=\"0\" id=\"dataTables-example_previous\">" +
                "<a href=\"/loja/pesquisar.do?page=0\">&lt;&lt;</a>" +
                "</li>" +
                "<li class=\"paginate_button\" tabindex=\"0\">" +
                "<a href=\"/loja/pesquisar.do?page=0\">1</a>" +
                "</li>" +
                "<li class=\"paginate_button\" tabindex=\"1\">" +
                "<a href=\"/loja/pesquisar.do?page=1\">2</a>" +
                "</li>" +
                "<li class=\"paginate_button\" tabindex=\"2\">" +
                "<a href=\"/loja/pesquisar.do?page=2\">3</a>" +
                "</li>" +
                "<li class=\"paginate_button next \" aria-controls=\"dataTables-example\" tabindex=\"0\" id=\"dataTables-example_next\">" +
                "<a href=\"/loja/pesquisar.do?page=1\">&gt;&gt;</a>" +
                "</li>" +
                "</ul>" +
                "</div>" +
                "</div>" +
                "</div>";

        assertEquals(EXPECTED, result);

    }

    @Test
    public void botaoNextHabilitado() {
        context.checking(new Expectations() {{
            oneOf(jspContext).getOut();
            will(returnValue(new MockJspWriter(writer)));

            allowing(jspContext).getRequest();
            will(returnValue(request));

            allowing(request);

            allowing(resultlist).getPageCount();
            will(returnValue(3L));

            allowing(resultlist).getPage();
            will(returnValue(1));

            allowing(resultlist).isPrevious();
            will(returnValue(Boolean.TRUE));

            allowing(resultlist).isNext();
            will(returnValue(Boolean.TRUE));
        }});


        PaginateTagLib paginate = new PaginateTagLib();
        paginate.setJspContext(jspContext);
        paginate.setResultList(resultlist);
        paginate.setUrl("/loja/pesquisar.do");

        String result = paginate.adicionarBotaoNext();

        final String EXPECTED =
                "<li class=\"paginate_button next \" aria-controls=\"dataTables-example\" tabindex=\"0\" id=\"dataTables-example_next\">" +
                        "<a href=\"/loja/pesquisar.do?page=2\">&gt;&gt;</a>" +
                        "</li>";

        assertEquals(EXPECTED, result);
    }

    @Test
    public void botaoNextDesabilitado() throws Exception {

        context.checking(new Expectations() {{
            oneOf(jspContext).getOut();
            will(returnValue(new MockJspWriter(writer)));

            allowing(jspContext).getRequest();
            will(returnValue(request));

            allowing(request);

            allowing(resultlist).getPageCount();
            will(returnValue(3L));

            allowing(resultlist).getPage();
            will(returnValue(2));

            allowing(resultlist).isPrevious();
            will(returnValue(Boolean.TRUE));

            allowing(resultlist).isNext();
            will(returnValue(Boolean.FALSE));
        }});


        PaginateTagLib paginate = new PaginateTagLib();
        paginate.setJspContext(jspContext);
        paginate.setResultList(resultlist);
        paginate.setUrl("/loja/pesquisar.do");

        String result = paginate.adicionarBotaoNext();

        final String EXPECTED =
                "<li class=\"paginate_button next disabled\" aria-controls=\"dataTables-example\" tabindex=\"0\" id=\"dataTables-example_next\">" +
                        "<a href=\"/loja/pesquisar.do?page=3\">&gt;&gt;</a>" +
                        "</li>";

        assertEquals(EXPECTED, result);

    }

    @Test
    public void testaMontagemsDaPaginacaoCentral() {

        context.checking(new Expectations() {{
            oneOf(jspContext).getOut();
            will(returnValue(new MockJspWriter(writer)));

            allowing(jspContext).getRequest();
            will(returnValue(request));

            allowing(request);

            allowing(resultlist).getPageCount();
            will(returnValue(50L));

            allowing(resultlist).getPage();
            will(returnValue(0));

            allowing(resultlist).isPrevious();
            will(returnValue(Boolean.FALSE));

            allowing(resultlist).isNext();
            will(returnValue(Boolean.TRUE));
        }});


        PaginateTagLib paginate = new PaginateTagLib();
        paginate.setJspContext(jspContext);
        paginate.setResultList(resultlist);
        paginate.setUrl("/loja/pesquisar.do");

        String result = paginate.adicionaLinhaCentralPaginacao();

        StringBuffer EXPECTED = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            EXPECTED.append(String.format("<li class=\"paginate_button\" tabindex=\"%s\"><a href=\"/loja/pesquisar.do?page=%s\">%s</a></li>", i, i, i + 1));
        }

        assertEquals(EXPECTED.toString(), result);


    }

    @Test
    public void testarRecuperaValoresRequest() {

        context.checking(new Expectations() {{
            oneOf(jspContext).getRequest();
            will(returnValue(request));

            oneOf(request).getParameterNames();
            will(returnEnumeration("object.nome"));

            oneOf(request).getParameter(with(aNonNull(String.class)));
            will(returnValue("teste"));
        }});


        PaginateTagLib paginate = new PaginateTagLib();
        paginate.setJspContext(jspContext);
        paginate.setResultList(resultlist);
        paginate.setUrl("/loja/pesquisar.do");

        String result = paginate.recuperaParametrosConsulta();

        assertEquals("&object.nome=teste", result);
    }

}



