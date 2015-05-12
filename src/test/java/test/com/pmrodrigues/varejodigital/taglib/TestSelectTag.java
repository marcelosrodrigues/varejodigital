package test.com.pmrodrigues.varejodigital.taglib;

import br.com.caelum.vraptor.validator.ValidationMessage;
import com.pmrodrigues.varejodigital.models.Secao;
import com.pmrodrigues.varejodigital.taglib.SelectTag;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.springframework.mock.web.MockJspWriter;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Marceloo on 23/01/2015.
 */
public class TestSelectTag {

    private final Mockery context = new Mockery() {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };

    private JspContext jspContext = context.mock(JspContext.class);
    private StringWriter writer = new StringWriter();

    @Test
    public void doTag() throws IOException, JspException {

        context.checking(new Expectations() {{
            allowing(jspContext).getOut();
            will(returnValue(new MockJspWriter(writer)));

            allowing(jspContext).getAttribute("errors", PageContext.REQUEST_SCOPE);
            will(returnValue(null));
        }});

        Collection<Secao> secoes = new ArrayList<>();
        Secao secao = new Secao();
        secao.setId(1L);
        secao.setNome("teste");
        secoes.add(secao);

        SelectTag select = new SelectTag();
        select.setId("teste");
        select.setLabel("loja.nome");
        select.setLabelField("nome");
        select.setValueField("id");
        select.setJspContext(jspContext);
        select.setList(secoes);

        select.doTag();
        final String EXPECTED = "<div class=\"form-group \"><label class=\"control-label\" for=\"teste\">Nome</label><select name=\"teste\" id=\"teste\" class=\"form-control\"><option></option><option value=\"1\" >teste</option></select></div>";
        assertEquals(EXPECTED, writer.toString());

    }

    @Test
    public void doTagWithValueSelected() throws IOException, JspException {
        context.checking(new Expectations() {{
            allowing(jspContext).getOut();
            will(returnValue(new MockJspWriter(writer)));

            allowing(jspContext).getAttribute("errors", PageContext.REQUEST_SCOPE);
            will(returnValue(null));
        }});

        Collection<Secao> secoes = new ArrayList<>();
        Secao secao = new Secao();
        secao.setId(1L);
        secao.setNome("teste");
        secoes.add(secao);

        SelectTag select = new SelectTag();
        select.setId("teste");
        select.setLabel("loja.nome");
        select.setLabelField("nome");
        select.setValueField("id");
        select.setJspContext(jspContext);
        select.setList(secoes);
        select.setValue(secao);

        select.doTag();
        final String EXPECTED = "<div class=\"form-group \"><label class=\"control-label\" for=\"teste\">Nome</label><select name=\"teste\" id=\"teste\" class=\"form-control\"><option></option><option value=\"1\" selected>teste</option></select></div>";
        assertEquals(EXPECTED, writer.toString());
    }

    @Test
    public void doTagWithError() throws IOException, JspException {

        final ValidationMessage error = context.mock(ValidationMessage.class);
        final List<ValidationMessage> errors = new ArrayList<>();
        errors.add(error);

        context.checking(new Expectations() {{
            allowing(jspContext).getOut();
            will(returnValue(new MockJspWriter(writer)));

            allowing(jspContext).getAttribute("errors", PageContext.REQUEST_SCOPE);
            will(returnValue(errors));

            oneOf(error).getCategory();
            will(returnValue("teste"));
        }});

        Collection<Secao> secoes = new ArrayList<>();
        Secao secao = new Secao();
        secao.setId(1L);
        secao.setNome("teste");
        secoes.add(secao);

        SelectTag select = new SelectTag();
        select.setId("teste");
        select.setLabel("loja.nome");
        select.setLabelField("nome");
        select.setValueField("id");
        select.setJspContext(jspContext);
        select.setList(secoes);
        select.setErrorField("teste");

        select.doTag();
        final String EXPECTED = "<div class=\"form-group has-error\"><label class=\"control-label\" for=\"teste\">Nome</label><select name=\"teste\" id=\"teste\" class=\"form-control\"><option></option><option value=\"1\" >teste</option></select></div>";
        assertEquals(EXPECTED, writer.toString());
    }
}
