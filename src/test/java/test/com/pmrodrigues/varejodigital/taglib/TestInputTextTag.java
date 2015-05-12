package test.com.pmrodrigues.varejodigital.taglib;

import br.com.caelum.vraptor.validator.ValidationMessage;
import com.pmrodrigues.varejodigital.taglib.InputTextTag;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.Test;
import org.springframework.mock.web.MockJspWriter;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.PageContext;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestInputTextTag {

    private final Mockery context = new Mockery() {
        {
            setImposteriser(ClassImposteriser.INSTANCE);
        }
    };

    private JspContext jspContext = context.mock(JspContext.class);
    private StringWriter writer = new StringWriter();

    @Test
    public void doTagWithOutError() throws Exception {

        context.checking(new Expectations() {{
            allowing(jspContext).getOut();
            will(returnValue(new MockJspWriter(writer)));

            allowing(jspContext).getAttribute("errors", PageContext.REQUEST_SCOPE);
            will(returnValue(null));
        }});


        final InputTextTag textview = new InputTextTag();
        textview.setJspContext(jspContext);
        String EXPECTED = "<div class=\"form-group \"><label for=\"object.nome\" class=\"control-label\">Nome</label><input type=\"text\" value=\"\" id=\"object.nome\" name=\"object.nome\" class=\"form-control\" placeholder=\"Nome\" /></div>";

        textview.setId("object.nome");
        textview.setLabel("departamento.nome");

        textview.doTag();

        assertEquals(EXPECTED, writer.toString());

    }

    @Test
    public void doTagWithError() throws Exception {

        final ValidationMessage error = context.mock(ValidationMessage.class);
        final List<ValidationMessage> errors = new ArrayList<>();
        errors.add(error);

        context.checking(new Expectations() {{
            allowing(jspContext).getOut();
            will(returnValue(new MockJspWriter(writer)));

            allowing(jspContext).getAttribute("errors", PageContext.REQUEST_SCOPE);
            will(returnValue(errors));

            oneOf(error).getCategory();
            will(returnValue("nome"));

        }});


        final InputTextTag textview = new InputTextTag();
        textview.setJspContext(jspContext);
        String EXPECTED = "<div class=\"form-group has-error\"><label for=\"object.nome\" class=\"control-label\">Nome</label><input type=\"text\" value=\"\" id=\"object.nome\" name=\"object.nome\" class=\"form-control\" placeholder=\"Nome\" /></div>";

        textview.setId("object.nome");
        textview.setLabel("departamento.nome");
        textview.setErrorField("nome");

        textview.doTag();

        assertEquals(EXPECTED, writer.toString());
    }
}